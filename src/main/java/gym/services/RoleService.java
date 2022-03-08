package gym.services;

import gym.model.Role;
import gym.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role saveRole(Role rol) {
        return roleRepository.save(rol);
    }
}
