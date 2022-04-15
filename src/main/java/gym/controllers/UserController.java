package gym.controllers;

import gym.dtos.UserCreateDto;
import gym.model.User;
import gym.repository.UserRepository;
import gym.services.UserService;
import gym.utils.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody @Validated UserCreateDto userDto) throws BusinessException {
        final User user = userService.createUser(userDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("api/users/" + user.getId()).toUriString());
        return ResponseEntity.created(uri).body(user);
    }
}
