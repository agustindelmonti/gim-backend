package gym.seeders;

import gym.dtos.UserCreateDto;
import gym.model.Role;
import gym.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UsersSeeder implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    Environment env;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = env.getProperty("application.admin.email");
        String adminPassword = env.getProperty("application.admin.password");
        String adminDni = env.getProperty("application.admin.dni");

        userService.createUser(new UserCreateDto(adminEmail, adminPassword, adminDni, Role.ADMINISTRADOR_ID));
    }
}