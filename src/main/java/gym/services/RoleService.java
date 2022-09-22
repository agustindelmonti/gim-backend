package gym.services;

import gym.model.Role;
import gym.model.User;
import gym.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
    public Role saveRole(Role rol) {
        return roleRepository.save(rol);
    }
}
