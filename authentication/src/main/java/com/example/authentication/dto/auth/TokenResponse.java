package com.example.authentication.dto.auth;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
    
}
