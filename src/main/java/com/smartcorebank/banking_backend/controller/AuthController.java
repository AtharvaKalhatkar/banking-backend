package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.dto.RegisterRequest;
import com.smartcorebank.banking_backend.entity.User; // <--- CRITICAL FIX
import com.smartcorebank.banking_backend.service.UserService;
import com.smartcorebank.banking_backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authenticationManager; // Requires SecurityConfig bean

    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest request) { // Use DTO
    return ResponseEntity.ok(userService.register(request));
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userService.findByEmail(email);
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Invalid credentials");
        }
    }
}