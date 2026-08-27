package com.example.auth.dto.user;

import lombok.Data;

@Data
public class UserCreateRequest {

    private Long organisationId;

    private String firstname;
    private String middlename;
    private String lastname;

    private String email;
    private String phone;

    private String gender;
    private String nationality;
}
