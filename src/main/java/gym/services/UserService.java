package gym.services;

import gym.dtos.UserCreateDto;
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

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoutineService routineService;

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

    /**
     * @return current logged user object
     */
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = principal.toString();
        if (principal instanceof User) {
            username = ((UserDetails) principal).getUsername();
        }
        return userRepository.findByEmail(username);
    }

    public User setRoutine(Long routineId) {
        User user = this.getCurrentUser();

        Routine routine = routineService.getById(routineId);

        user.setRoutine(routine);
        return userRepository.save(user);
    }
}
