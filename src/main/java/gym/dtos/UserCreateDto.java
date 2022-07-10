package gym.dtos;

import gym.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class UserCreateDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nroDoc;

    @NotBlank
    private String name;

    @Positive
    @NotNull
    private Long rolId;

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNroDoc(this.nroDoc);
        user.setName(this.name);
        return user;
    }
}
