package com.example.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.auth.models.AuthUser;

@Repository
public interface AuthUserRepo extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
}
