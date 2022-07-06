package gym.seeders;

import gym.dtos.UserCreateDto;
import gym.model.Role;
import gym.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsersSeeder implements CommandLineRunner {
    private UserService userService;
    private Environment env;

    @Override
    public void run(String... args) {
        String adminEmail = env.getProperty("application.admin.email");
        String adminPassword = env.getProperty("application.admin.password");
        String adminDni = env.getProperty("application.admin.dni");

        try {
            userService.loadUserByUsername(adminEmail);
        } catch (Exception exception) {
            userService.createUser(new UserCreateDto(adminEmail, adminPassword, adminDni, Role.ADMIN_ID));
        }
    }
}