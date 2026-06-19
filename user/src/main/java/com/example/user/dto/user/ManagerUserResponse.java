package com.example.user.dto.user;

import java.time.Instant;
import lombok.Data;

@Data
public class ManagerUserResponse {

    private Long id;
    private String username;
    private String email;
    
    private Long organisationId;
    private String organisationName;
    
    private Long roleId;
    private String roleName;

    private boolean isActive;
    private boolean isVerified;

    private Instant createdAt;
}