package com.example.auth.dto.token;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
    @NotBlank
    String refreshToken
) { }
