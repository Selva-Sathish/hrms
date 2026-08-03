package com.example.user.dto.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchRequest {
    @NotBlank(message = "username is required")
    private String username;
    
    @Email
    @NotBlank(message = "email is required")
    private String email;
    private LocalDate dateOfBirth;

    @NotBlank(message = "role is required")
    private Long roleId;

    private Boolean deleted;
    private Boolean active;
    private Boolean verified;
    private Boolean accountLocked;
}