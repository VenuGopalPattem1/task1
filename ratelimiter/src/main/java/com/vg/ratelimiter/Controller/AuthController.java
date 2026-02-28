package com.vg.ratelimiter.Controller;

import org.springframework.web.bind.annotation.*;

import com.vg.ratelimiter.Entity.User;
import com.vg.ratelimiter.repo.UserRepository;
import com.vg.ratelimiter.utils.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password) {

        if (userRepository.findByUsername(username).isPresent()) {
            return "User already exists";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // NOTE: plain text (for simplicity)

        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty() ||
            !user.get().getPassword().equals(password)) {
            return "Invalid credentials";
        }

        return JwtUtil.generateToken(username);
    }
}