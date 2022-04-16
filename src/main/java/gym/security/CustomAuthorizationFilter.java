package gym.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
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
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private Environment env;
    private UserDetailsService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getServletPath().equals("/api/login") ||
                request.getServletPath().equals("/api/refreshToken")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String prefix = env.getProperty("application.jwt.token.prefix") + " ";

            if (authorizationHeader != null && authorizationHeader.startsWith(prefix)) {
                try {
                    String token = authorizationHeader.substring(prefix.length());
                    String secret = env.getProperty("application.jwt.secret");
                    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    String username = decodedJWT.getSubject();
                    Collection<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));

                    UserDetails userDetails = userService.loadUserByUsername(username);
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception e) {
                    log.error("Error tratando de autorizar jwt {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }

                filterChain.doFilter(request, response);
            }
        }

    }

}
