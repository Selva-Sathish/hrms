package com.example.user.dto.user;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchRequest {

    private String username;
    private String email;
    private String password;
    private LocalDate dateOfBirth;

    private Long roleId;

    private Boolean isDeleted;
    private Boolean isActive;
    private Boolean isVerified;
    private Boolean accountLocked;
}