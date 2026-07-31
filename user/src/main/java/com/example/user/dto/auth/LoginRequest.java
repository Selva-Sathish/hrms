package com.example.user.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @Email
    String email,
    @NotBlank
    @Size(min = 8, message = "password atleast character")
    String password
) {}
