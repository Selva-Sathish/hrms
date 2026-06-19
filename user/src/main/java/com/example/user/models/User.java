package com.example.user.models;

import java.time.Instant;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
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
    
    @Column(nullable = false)
    private String password;

    private LocalDate dateOfBirth;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    private Organisation organisation; 

    @OneToOne(cascade = CascadeType.REMOVE)
    private Role role;

    private boolean isDeleted;

    @CreationTimestamp
    @Column(insertable = false, updatable = false)
    private Instant createdAt;

    private Instant DeletedAt;
    
    @UpdateTimestamp
    @Column(insertable = false)
    private Instant updatedAt;

    private boolean isActive;
    
    private boolean isVerified;

    private boolean accountLocked;

    private boolean failedLoginAttempts;

    private Instant lockedAt;
    
    private Instant lastLogin;

}
