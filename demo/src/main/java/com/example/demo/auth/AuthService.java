package com.example.demo.auth;

import com.example.demo.security.JwtService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwt;

    public AuthService(UserRepository users, PasswordEncoder encoder,
            AuthenticationManager authManager, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    public AuthResponse register(RegisterRequest req) {
        if (users.existsByEmail(req.email())) {
            throw new EmailAlreadyRegisteredException(req.email());
        }

        User u = new User();
        u.setFirstName(req.firstName());
        u.setLastName(req.lastName());
        u.setPhoneNumber(req.phoneNumber());
        u.setGender(req.gender());
        u.setAddress(req.address());
        u.setEmail(req.email());
        u.setPassword(encoder.encode(req.password()));
        u.setRoles(Set.of("USER")); // default role
        // surname will be auto-built by @PrePersist/@PreUpdate in your entity
        users.save(u);

        String token = jwt.generateToken(u.getEmail());
        return new AuthResponse(
                u.getId(), u.getFirstName(), u.getLastName(), u.getSurname(),
                u.getEmail(), u.getPhoneNumber(), u.getGender(), u.getAddress(),
                u.getRoles(), token);
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        User u = users.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwt.generateToken(u.getEmail());
        return new AuthResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getSurname(),
                u.getEmail(),
                u.getPhoneNumber(),
                u.getGender(),
                u.getAddress(),
                u.getRoles(),
                token);
    }
}
