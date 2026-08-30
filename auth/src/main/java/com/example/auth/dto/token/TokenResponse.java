package com.example.auth.dto.token;

import jakarta.validation.constraints.NotBlank;

public record TokenResponse(
    @NotBlank
    String accessToken,
    
    @NotBlank
    String refreshToken
){
    
}
