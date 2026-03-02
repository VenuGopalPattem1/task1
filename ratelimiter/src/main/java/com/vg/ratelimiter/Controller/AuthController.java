package com.vg.ratelimiter.Controller;

import org.springframework.web.bind.annotation.*;

import com.vg.ratelimiter.Entity.AuthUser;
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
    public String register(@RequestBody AuthUser user) {

        if (userRepository.findByUsername(user.getName()).isPresent()) {
            return "User already exists";
        }

        User us = new User();
        us.setUsername(user.getName());
        us.setPassword(user.getPassword()); // NOTE: plain text (for simplicity)

        userRepository.save(us);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthUser auth) {

        Optional<User> user = userRepository.findByUsername(auth.getName());

        if (user.isEmpty() ||
            !user.get().getPassword().equals(auth.getPassword())) {
            return "Invalid credentials";
        }

        return JwtUtil.generateToken(auth.getName());
    }
}