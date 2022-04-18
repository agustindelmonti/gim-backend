package gym.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final String PREFIX = "Bearer ";
    private static final String[] PUBLIC_ROUTES = {"/api/login", "/api/refreshToken"};

    private Environment env;
    private UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (Arrays.asList(PUBLIC_ROUTES).contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX)) {
            log.debug("The request does not provide a bearer token");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(PREFIX, "");
        try {
            String secret = env.getProperty("application.jwt.secret");
            assert secret != null;

            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String username = decodedJWT.getSubject();
            UserDetails userDetails = userService.loadUserByUsername(username);

            Collection<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            Collection<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (JWTVerificationException e) {
            log.debug(e.getMessage());
        }

        filterChain.doFilter(request, response);


    }

}
