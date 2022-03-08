package gym.dtos;

import gym.model.User;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserCreateDto {
    public UserCreateDto(String email, String password, String nroDoc, Long rolId) {
        this.email = email;
        this.password = password;
        this.nroDoc = nroDoc;
        this.rolId = rolId;
    }


    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nroDoc;

    @Positive
    @NotNull
    private Long rolId;

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNroDoc(this.nroDoc);
        return user;
    }
}
