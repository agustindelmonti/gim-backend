package gym.dtos;

import gym.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@RequiredArgsConstructor
public class UserCreateDto {
    @NonNull
    @Email
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String nroDoc;

    @NonNull
    private String name;

    @NonNull
    private Long rolId;

    private Long routineId;

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setNroDoc(this.nroDoc);
        user.setName(this.name);
        return user;
    }
}
