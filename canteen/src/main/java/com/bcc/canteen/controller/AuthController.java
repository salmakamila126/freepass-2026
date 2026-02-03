package com.bcc.canteen.controller;

import com.bcc.canteen.entity.User;
import com.bcc.canteen.entity.Role;
import com.bcc.canteen.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setRole(Role.USER); // default role
        return userRepository.save(user);
    }
}
