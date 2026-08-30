package com.example.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

    @Email
    @NotBlank
    String email,

    @NotBlank
    String organisation,

    @NotBlank
    @Size(min = 8, max = 25)
    String password,

    @NotBlank
    String firstname,

    String middlename,

    @NotBlank
    String lastname,

    String phone,

    String gender,

    String nationality
) {}
