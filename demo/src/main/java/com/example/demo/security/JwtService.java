package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expMinutes;

    public JwtService(
            @Value("${app.jwt.secret}") String secretBase64,
            @Value("${app.jwt.exp-minutes:60}") long expMinutes) {

        // 1) Decode Base64
        byte[] keyBytes = Decoders.BASE64.decode(secretBase64);

        // 2) Build key (validates size >= 256 bits for HS256)
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.expMinutes = expMinutes;
    }

    public String generateToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expMinutes * 60)))
                .signWith(key, Jwts.SIG.HS256) // explicit alg
                .compact();
    }

    public String extractSubject(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
