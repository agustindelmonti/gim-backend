package gym.controller;

import gym.model.User;
import gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }
}
