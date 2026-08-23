package com.example.authentication.security.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetailPrinciple {
    private Long userId;
    private Long organisationId;    
    private String role;
}
