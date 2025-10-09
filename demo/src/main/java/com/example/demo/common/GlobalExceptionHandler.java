package com.example.demo.common;

import com.example.demo.auth.EmailAlreadyRegisteredException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

        record ApiError(Instant timestamp, int status, String error, String message, String path,
                        Map<String, String> fields) {
        }

        @ExceptionHandler(EmailAlreadyRegisteredException.class)
        public ResponseEntity<ApiError> emailExists(EmailAlreadyRegisteredException ex,
                        jakarta.servlet.http.HttpServletRequest req) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                new ApiError(Instant.now(), 409, "Conflict", ex.getMessage(), req.getRequestURI(),
                                                null));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                        HttpServletRequest req) {
                String message = "Duplicate value violates a unique constraint.";

                if (ex.getMessage().contains("users_email_key")) {
                        message = "Email already exists.";
                }

                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(Map.of(
                                                "timestamp", Instant.now(),
                                                "status", 409,
                                                "error", "Conflict",
                                                "message", message,
                                                "path", req.getRequestURI()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex,
                        jakarta.servlet.http.HttpServletRequest req) {
                Map<String, String> fieldErrors = new LinkedHashMap<>();
                for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
                        fieldErrors.put(fe.getField(), fe.getDefaultMessage());
                }
                return ResponseEntity.badRequest().body(
                                new ApiError(Instant.now(), 400, "Bad Request", "Validation failed",
                                                req.getRequestURI(), fieldErrors));
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiError> badCreds(BadCredentialsException ex,
                        jakarta.servlet.http.HttpServletRequest req) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                                new ApiError(Instant.now(), 401, "Unauthorized", "Invalid email or password",
                                                req.getRequestURI(),
                                                null));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiError> illegalArg(IllegalArgumentException ex,
                        jakarta.servlet.http.HttpServletRequest req) {
                return ResponseEntity.badRequest().body(
                                new ApiError(Instant.now(), 400, "Bad Request", ex.getMessage(), req.getRequestURI(),
                                                null));
        }
}
