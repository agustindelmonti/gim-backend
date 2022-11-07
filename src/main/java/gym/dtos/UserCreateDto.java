package gym.dtos;

import gym.model.User;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
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

    @NonNull
    private Long rolId;

    public UserCreateDto(String email, String password, String nroDoc, String name, @NonNull Long rolId) {
        this.email = email;
        this.password = password;
        this.nroDoc = nroDoc;
        this.name = name;
        this.rolId = rolId;
    }

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNroDoc(this.nroDoc);
        user.setName(this.name);
        return user;
    }
}
