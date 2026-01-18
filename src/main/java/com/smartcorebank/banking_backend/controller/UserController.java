package com.smartcorebank.banking_backend.controller;

import com.smartcorebank.banking_backend.dto.RegisterRequest; // Import the DTO
import com.smartcorebank.banking_backend.entity.User;
import com.smartcorebank.banking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // FIX: Changed parameter from 'User' to 'RegisterRequest'
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }
}