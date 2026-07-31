package com.example.user.models;

import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
    name = "users",
    indexes = {
        @Index(
            unique = true,
            name = "idx_user_email",
            columnList = "email"
        )
    }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    
    @Column(unique = true)
    private String email;
    
    private String password;

    private LocalDate dateOfBirth;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id", nullable = false)
    private Organisation organisation; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private boolean deleted;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant deletedAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

    private boolean active;
    
    private boolean verified;

    private boolean accountLocked;

    private int failedLoginAttempts;

    private Instant lockedAt;
    
    private Instant lastLogin;

}
