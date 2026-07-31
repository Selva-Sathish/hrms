package com.example.user.dto.auth;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
    
}
