package com.example.user.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.common.ApiResponse;
import com.example.user.dto.user.CreateUserRequest;
import com.example.user.dto.auth.LoginRequest;
import com.example.user.dto.auth.TokenResponse;
import com.example.user.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }   

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(
        @Valid @RequestBody CreateUserRequest request
    ){
        authService.registerUser(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request){
        TokenResponse token = authService.login(request);
        
        ResponseCookie at = ResponseCookie.from("access_token", token.accessToken())
            .httpOnly(true)
            .secure(false)
            .path("/")
            .sameSite("Lax")
            .maxAge(Duration.ofMinutes(15))
            .build();
        
        ResponseCookie rt = ResponseCookie.from("refresh_token", token.refreshToken())
            .httpOnly(true)
            .secure(false)
            .sameSite("Lax")
            .path("/")
            .maxAge(Duration.ofDays(7))
            .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, at.toString());
        httpHeaders.add(HttpHeaders.SET_COOKIE, rt.toString());
        
        return ResponseEntity
            .ok()
            .headers(httpHeaders)
            .body(new ApiResponse<TokenResponse>(true, "Login successfull", null));
    
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refresh(
        @CookieValue("refresh_token") String refreshToken
    ){
        System.out.println(refreshToken);
        ResponseCookie token = authService.createRefreshToken(refreshToken);   
        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, token.toString())
            .body(new ApiResponse<>(true, "login successful", null));
    }

}
