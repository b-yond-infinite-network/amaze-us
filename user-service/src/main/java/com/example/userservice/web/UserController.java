package com.example.userservice.web;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
