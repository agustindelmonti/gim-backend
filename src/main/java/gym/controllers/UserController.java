package gym.controllers;

import gym.dtos.UserCreateDto;
import gym.dtos.UserUpdateDto;
import gym.model.User;
import gym.services.UserService;
import gym.utils.ApplicationException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController()
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearer")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/nro-doc/{nroDoc}")
    public User getByNroDoc(@PathVariable String nroDoc) {
        return userService.getByNroDoc(nroDoc);
    }

    @GetMapping("/me")
    public User me(@AuthenticationPrincipal User user) {
        return userService.getById(user.getId());
    }


    @GetMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody @Validated UserCreateDto userDto) throws ApplicationException {
        final User user = userService.createUser(userDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath().path("api/users/" + user.getId()).toUriString());
        return ResponseEntity.created(uri).body(user);
    }



    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody @Validated UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PostMapping("{id}/update-payment")
    public User updatePayment(@PathVariable("id") Long id) {
        return userService.updatePayment(id);
    }
}
