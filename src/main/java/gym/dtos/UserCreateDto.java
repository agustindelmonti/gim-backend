package gym.dtos;

import gym.model.User;

import javax.validation.constraints.*;

public class UserCreateDto {

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String password;

    @NotBlank
    public String nroDoc;

    @Positive
    @NotNull
    public Long rolId;

    public User toUser() {
        User user = new User();

        user.email = this.email;
        user.password = this.password;
        user.nroDoc = this.nroDoc;

        return user;
    }
}
