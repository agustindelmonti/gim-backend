package gym.services;

import gym.dtos.UserCreateDto;
import gym.model.Role;
import gym.model.User;
import gym.repository.RoleRepository;
import gym.repository.UserRepository;
import gym.utils.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public User createCliente(UserCreateDto userDto) throws BusinessException {
        if (userRepository.existsByEmail(userDto.email)) {
            throw new BusinessException("Email en uso");
        }

        if (userRepository.existsByNroDoc(userDto.nroDoc)) {
            throw new BusinessException("Nro Documento en uso");
        }

        User user = userDto.toUser();

        final Role role = roleRepository.findById(userDto.rolId).orElseThrow();
        user.roles.add(role);

        final User created = userRepository.save(user);
        return created;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email);
        final Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

}
