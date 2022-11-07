package gym.controllers;

import gym.dtos.UserCreateDto;
import gym.dtos.UserPasswordDto;
import gym.dtos.UserUpdateDto;
import gym.model.User;
import gym.services.UserService;
import gym.users.SuccessfulUserRegistrationEvent;
import gym.utils.ApplicationException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearer")
public class UserController {
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping()
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping(headers = "X-API-VERSION=1")
    public Page<User> getUsers(Optional<UserFilter> filter, Pageable pageable) {
        UserSpecification spec = new UserSpecification(filter.orElse(null));
        return userService.getAll(spec, pageable);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping("/nro-doc/{nroDoc}")
    public User getByNroDoc(@PathVariable String nroDoc) {
        return userService.getByNroDoc(nroDoc);
    }

    @GetMapping("/me")
    public User me(@AuthenticationPrincipal User user) {
        return userService.getById(user.getId());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody @Validated UserCreateDto userDto) throws ApplicationException {
        final User user = userService.createUser(userDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("api/users/" + user.getId()).toUriString());

        applicationEventPublisher.publishEvent(new SuccessfulUserRegistrationEvent(this, user));

        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody @Validated UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PutMapping("/{user}/roles/add/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRoleToUser(@PathVariable("user") Long userId, @PathVariable("role") Long roleId) {
        userService.addRoleToUser(userId, roleId);
    }

    /**
     * Changes the current user password
     */
    @PutMapping("/updatePassword")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody UserPasswordDto changePasswordDto,
            @AuthenticationPrincipal User user) {

        if (userService.checkIfValidOldPassword(user, changePasswordDto.getOldPassword())) {
            return ResponseEntity.badRequest().body("Wrong password");
        }
        if (!changePasswordDto.checkPasswordsMatch()) {
            return ResponseEntity.badRequest().body("New passwords provided do not match");
        }
        userService.changeUserPassword(user, changePasswordDto.getNewPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<User> findByNameLike(@RequestParam("name") String name) {
        return userService.searchByNameLike(name);
    }
}
