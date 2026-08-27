package com.example.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.common.ApiResponse;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(
        @Valid @RequestBody RegisterRequest request
    ){
        System.out.println("hit the register endpoint");
        authService.registerUser(request);
        return ResponseEntity
            .ok()
            .body(
                new ApiResponse<>(true, "user registered successfully", null)
            );
    }
}
