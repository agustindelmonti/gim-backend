package gym.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor

public class UserUpdateDto {
    @NotBlank
    @Email
    public String email;

    public String password;

    @NotBlank
    public String nroDoc;

    @NotBlank
    public String name;

    @Positive
    @NotNull
    public Long rolId;

    public Long routineId;
}
