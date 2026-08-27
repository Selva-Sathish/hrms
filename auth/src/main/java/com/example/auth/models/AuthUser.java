package com.example.auth.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "auth_user",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_auth_user_username", columnNames = "username"
        )
    } 
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;
    private String email;
    private String password;
    
    private boolean enabled;

    private int loginAttempts;

    private Instant lockUntilAt;
    private Instant passwordChangedAt;
    private Instant lastLoginAt;
    private Instant createdAt;
    private Instant updatedAt;
}
