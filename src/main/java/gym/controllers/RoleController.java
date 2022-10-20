package gym.controllers;

import gym.model.Role;
import gym.services.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/roles")
@SecurityRequirement(name = "bearer")
public class RoleController {
    private final RoleService roleService;

    @GetMapping()
    public List<Role> getRoles() {
        return roleService.getAll();
    }
}
