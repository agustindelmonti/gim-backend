package gym.controllers;

import gym.dtos.UserCreateDto;
import gym.model.User;
import gym.repository.UserRepository;
import gym.services.UserService;
import gym.utils.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping(path = "users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping()
    public User createCliente(@RequestBody @Validated UserCreateDto userDto) throws BusinessException {
        return userService.createCliente(userDto);
    }
}
