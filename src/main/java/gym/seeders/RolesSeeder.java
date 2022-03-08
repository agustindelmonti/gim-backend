package gym.seeders;

import gym.model.Role;
import gym.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesSeeder implements CommandLineRunner {
    @Autowired
    RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        roleService.saveRole(new Role(Role.ADMINISTRADOR_ID, "Administrador"));
        roleService.saveRole(new Role(Role.CLIENTE_ID, "Cliente"));
    }
}
