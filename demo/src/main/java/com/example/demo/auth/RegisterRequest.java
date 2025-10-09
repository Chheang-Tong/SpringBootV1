package com.example.demo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
                @NotBlank String firstName,
                @NotBlank String lastName,
                String phoneNumber,
                String gender,
                String address,
                @Email @NotBlank String email,
                @NotBlank @Size(min = 8, max = 128) String password) {
}
