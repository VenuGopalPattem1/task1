package com.vg.ratelimiter.Controller;

import org.springframework.web.bind.annotation.*;

import com.vg.ratelimiter.service.RateLimiterService;
import com.vg.ratelimiter.utils.JwtUtil;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final RateLimiterService rateLimiter;

    public BookingController(RateLimiterService rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @PostMapping("/book")
    public String book(@RequestHeader("Authorization") String header,
                       @RequestParam String movie,
                       @RequestParam int seats) {

        if (header == null || !header.startsWith("Bearer ")) {
            return "Unauthorized";
        }

        String token = header.substring(7);

        String username;
        try {
            username = JwtUtil.extractUsername(token);
        } catch (Exception e) {
            return "Invalid or expired token";
        }

        if (!rateLimiter.allow(username)) {
            return "Too many booking attempts. Try later.";
        }

        return "Booking successful for " + movie + " by " + username;
    }
}