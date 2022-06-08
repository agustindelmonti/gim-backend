package gym.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Objects;

@Configuration
public class JWToken {
    private static final int EXPIRATION = 3600 * 60 * 1000;

    private final Algorithm algorithm;

    public JWToken(Environment env) {
        String secret = Objects.requireNonNull(env.getProperty("application.jwt.secret"));
        algorithm = Algorithm.HMAC256(secret.getBytes());
    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String accessToken(UserDetails user, String issuer) throws JWTCreationException {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
    }

    public String refreshToken(UserDetails user, String issuer) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .withIssuer(issuer)
                .sign(algorithm);
    }
}
