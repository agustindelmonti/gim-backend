package gym.services;

import gym.dtos.UserCreateDto;
import gym.model.User;
import gym.repository.RoleRepository;
import gym.repository.UserRepository;
import gym.utils.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository rolRepository;

    public User createCliente(UserCreateDto userDto) throws BusinessException {
        if (userRepository.existsByEmail(userDto.email)) {
            throw new BusinessException("Email en uso");
        }

        if (userRepository.existsByNroDoc(userDto.nroDoc)) {
            throw new BusinessException("Nro Documento en uso");
        }

        User user = userDto.toUser();

        user.rol = rolRepository.findById(userDto.rolId).orElseThrow();

        return userRepository.save(user);
    }
}
