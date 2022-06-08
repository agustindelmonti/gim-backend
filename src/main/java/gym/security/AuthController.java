package gym.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import gym.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/api")
@Log4j2
@AllArgsConstructor
public class AuthController {
    private static final String PREFIX = "Bearer ";

    private final Environment env;
    private final UserService userService;

    /**
     * Refreshes the jwt access token
     */
    @PostMapping("/refreshToken")
    public JWTAuthenticationPayload refreshToken(@RequestHeader("Authorization") String authorizationHeader, HttpServletRequest request) throws Exception {

        if (!authorizationHeader.startsWith(PREFIX)) {
            throw new Exception();
        }
        String refresh_token = authorizationHeader.replace(PREFIX, "");

        var verifier = new JWToken(env);
        DecodedJWT decodedJWT = verifier.verify(refresh_token);

        String username = decodedJWT.getSubject();
        UserDetails user = userService.loadUserByUsername(username);

        String access_token = verifier.accessToken(user, request.getRequestURI());

        return new JWTAuthenticationPayload(access_token, refresh_token);
    }
}
