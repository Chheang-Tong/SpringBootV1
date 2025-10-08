package com.example.demo.common;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    private final UserRepository users;

    public CurrentUser(UserRepository users) {
        this.users = users;
    }

    public User require(Authentication auth) {
        return users.findByEmail(auth.getName()).orElseThrow();
    }
}
