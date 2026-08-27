package com.example.user.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "users",
    indexes = {
        @Index(name = "idx_users_organisation_id", columnList = "organisation_id"),
        @Index(name = "idx_users_email", columnList = "email")
    }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long organisationId;

    @Column(nullable = false)
    private String firstname;
    private String middlename;
    
    @Column(nullable = false)
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;
    
    private String phone;

    private String gender;

    private String profilePhoto;
    
    private String nationality;
    
    private Instant createdAt;
    private Instant updatedAt;

    private boolean deleted;

}
