package gym.seeders;

import gym.model.Role;
import gym.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RolesSeeder implements CommandLineRunner {
    private RoleService roleService;

    @Override
    public void run(String... args) {
        roleService.saveRole(new Role(Role.ADMIN_ID, "Admin"));
        roleService.saveRole(new Role(Role.USER_ID, "User"));
    }
}
