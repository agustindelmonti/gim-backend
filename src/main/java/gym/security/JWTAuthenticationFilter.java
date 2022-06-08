package gym.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final Environment env;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthenticationCredentials auth;
        try {
            String sb = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            ObjectMapper mapper = new ObjectMapper();
            auth = mapper.readValue(sb, AuthenticationCredentials.class);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("Bad request");
        }

        var token = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException {

        UserDetails user = (UserDetails) authResult.getPrincipal();

        var t = new JWToken(env);
        String access_token = t.accessToken(user, request.getRequestURI());
        String refresh_token = t.refreshToken(user, request.getRequestURI());
        var payload = new JWTAuthenticationPayload(access_token, refresh_token);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), payload);
    }
}
