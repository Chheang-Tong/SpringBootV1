package com.example.demo.auth;

import java.util.Set;

public record AuthResponse(
        Long id,
        String firstName,
        String lastName,
        String surname,
        String email,
        String phoneNumber,
        String gender,
        String address,
        Set<String> roles,

        String accessToken) {
}
