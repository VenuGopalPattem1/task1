package com.vg.ratelimiter.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW = 60 * 1000;

    private final Map<String, UserRequest> users = new HashMap<>();

    public synchronized boolean allow(String username) {

        long now = System.currentTimeMillis();

        if (!users.containsKey(username)) {
            users.put(username, new UserRequest(1, now));
            return true;
        }

        UserRequest data = users.get(username);

        if (now - data.startTime > WINDOW) {
            data.count = 1;
            data.startTime = now;
            return true;
        }

        if (data.count < MAX_REQUESTS) {
            data.count++;
            return true;
        }

        return false;
    }

    static class UserRequest {
        int count;
        long startTime;

        UserRequest(int count, long startTime) {
            this.count = count;
            this.startTime = startTime;
        }
    }
}