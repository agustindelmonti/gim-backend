package gym.security;

import lombok.Data;

@Data
public class AuthenticationCredentials {
    private String username;
    private String password;
}
