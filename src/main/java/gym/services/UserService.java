package gym.services;

import gym.BusinessException;
import gym.dtos.UserCreateDto;
import gym.model.Rol;
import gym.model.User;
import gym.repository.RolRepository;
import gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RolRepository rolRepository;


    public User createCliente(UserCreateDto userDto) throws BusinessException{
        if (userRepository.existsByEmail(userDto.email)) {
            throw new BusinessException("Email en uso");
        }

        if(userRepository.existsByNroDoc(userDto.nroDoc)) {
            throw new BusinessException("Nro Documento en uso");
        }


        User user = userDto.toUser();

        user.rol =  rolRepository.findById(userDto.rolId).orElseThrow();

        return userRepository.save(user);
    }
}
