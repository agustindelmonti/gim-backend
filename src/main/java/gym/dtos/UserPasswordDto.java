package gym.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserPasswordDto {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

    public boolean checkPasswordsMatch() {
        return newPassword.equals(confirmPassword);
    }

}
