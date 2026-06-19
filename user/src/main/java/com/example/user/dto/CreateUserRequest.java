package com.example.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateUserRequest(

        @NotBlank(message = "user name is required")
        String username,

        @NotBlank(message = "password is required")
        @Size(min = 8, message = "password must be at least 8 characters")
        String password,

        @NotBlank(message = "confirm password is required")
        @Size(min = 8, message = "password must be at least 8 characters")
        String confirmPassword,

        @NotBlank(message = "email is required")
        @Email(message = "invalid email format")
        String email,

        @NotBlank(message = "organisation is required")
        String organisation

) {}


