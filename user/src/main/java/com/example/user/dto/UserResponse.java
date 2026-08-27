package com.example.user.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private Long organisationId;

    private String firstname;
    private String middlename;
    private String lastname;

    private String email;
    private String phone;

    private String gender;
    private String profilePhoto;
    private String nationality;

    private Instant createdAt;
    private Instant updatedAt;

}