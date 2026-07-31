package com.example.user.dto.user;

import java.time.Instant;
import java.time.LocalDate;

import lombok.Data;

@Data
public class AdminUserResponse {
    private Long id;
    private String username;
    private String email;
    private LocalDate dateOfBirth;

    private Long organisationId;
    private String organisationName;
    
    private Long roleId;
    private String roleName;

    private boolean deleted;
    private Instant deletedAt;

    private boolean active;
    private boolean verified;
    private boolean accountLocked;

    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
}
