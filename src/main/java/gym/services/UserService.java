package gym.services;

import gym.dtos.UserCreateDto;
import gym.dtos.UserUpdateDto;
import gym.model.Role;
import gym.model.User;
import gym.repository.RoleRepository;
import gym.repository.UserRepository;
import gym.utils.ApplicationException;
import gym.utils.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User getById(Long id) throws NotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User getByNroDoc(String nroDoc) throws NotFoundException {
        return userRepository.findByNroDoc(nroDoc).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Page<User> getAll(Specification<User> specs, Pageable page) {
        return userRepository.findAll(specs, page);
    }

    public List<User> searchByNameLike(String name) {
        return userRepository.findAllByNameContainingIgnoreCase(name);
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
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        User user = getById(userId);
        final Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(String.format("Role %s not found", roleId)));
        user.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
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
        return userRepository.save(user);
    }

    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(user.getPassword(), oldPassword);
    }
}
