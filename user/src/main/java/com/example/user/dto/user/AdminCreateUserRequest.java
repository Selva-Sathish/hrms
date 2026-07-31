package com.example.user.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminCreateUserRequest {
    @NotBlank(message = "username is required")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "email is required")
    private String email;
    
    @NotNull(message = "role is required")
    private Long roleId;
}
