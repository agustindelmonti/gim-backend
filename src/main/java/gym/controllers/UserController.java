package gym.controllers;

import gym.dtos.UserCreateDto;
import gym.dtos.UserProfileDto;
import gym.model.User;
import gym.repository.UserRepository;
import gym.services.UserService;
import gym.utils.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Secured("ROLE_ADMIN")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/me")
    public UserProfileDto me(@AuthenticationPrincipal User user) {
        return new UserProfileDto(user);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody @Validated UserCreateDto userDto) throws ApplicationException {
        final User user = userService.createUser(userDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("api/users/" + user.getId()).toUriString());
        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/routine")
    public long setRoutine(@RequestBody long routineId) {
        return routineId;
        //return userService.setRoutine(routineId);
    }
}
