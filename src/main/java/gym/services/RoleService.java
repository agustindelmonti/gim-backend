package gym.services;

import gym.model.Role;
import gym.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role saveRole(Role rol) {
        return roleRepository.save(rol);
    }
}
