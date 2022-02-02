package gym.services;

import gym.dtos.UserCreateDto;
import gym.model.Role;
import gym.model.User;
import gym.repository.RoleRepository;
import gym.repository.UserRepository;
import gym.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(username);
        if (user == null) {
            log.error("No se encontró un usuario registrado con email {}", username);
            throw new UsernameNotFoundException("No se encontró un usuario registrado");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.name)));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
