package gym.services;

import gym.dtos.UserCreateDto;
import gym.dtos.UserUpdateDto;
import gym.model.Role;
import gym.model.Routine;
import gym.model.User;
import gym.repository.RoleRepository;
import gym.repository.UserRepository;
import gym.utils.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoutineService routineService;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByNroDoc(String nroDoc) {
        return userRepository.findByNroDoc(nroDoc);
    }

    public User createUser(UserCreateDto userDto) throws ApplicationException {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ApplicationException("Email en uso");
        }

        if (userRepository.existsByNroDoc(userDto.getNroDoc())) {
            throw new ApplicationException("Nro Documento en uso");
        }

        User user = userDto.toUser();

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final Role role = roleRepository.findById(userDto.getRolId()).orElseThrow();
        user.getRoles().add(role);

        if (userDto.getRoutineId() != null) {
            Routine routine = routineService.getById(userDto.getRoutineId());
            user.setRoutine(routine);
        }

        return userRepository.save(user);
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
            throw new UsernameNotFoundException(username);
        }
        return user;
    }


    public User getById(Long id) {
        return this.userRepository.findById(id).orElseThrow();
    }
    public User updatePayment(Long id) {
        User user = this.getById(id);
        user.setPayment(new Date());

        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = this.getById(id);

        if (!user.getEmail().equals(userUpdateDto.getEmail()) && userRepository.existsByEmail(userUpdateDto.getEmail())) {
            throw new ApplicationException("Email en uso");
        }

        if (!user.getNroDoc().equals(userUpdateDto.getNroDoc()) && userRepository.existsByNroDoc(userUpdateDto.getNroDoc())) {
            throw new ApplicationException("Nro Documento en uso");
        }

        user.setName(userUpdateDto.name);
        user.setEmail(userUpdateDto.email);
        user.setNroDoc(userUpdateDto.nroDoc);


        final Role role = roleRepository.findById(userUpdateDto.rolId).orElseThrow();
        user.getRoles().clear();
        user.getRoles().add(role);

        if (userUpdateDto.routineId != null) {
            Routine routine = routineService.getById(userUpdateDto.routineId);

            user.setRoutine(routine);
        }

        return userRepository.save(user);
    }
}
