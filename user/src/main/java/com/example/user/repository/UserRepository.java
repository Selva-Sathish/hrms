package com.example.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.user.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    boolean existsByEmail(String name);

    Optional<User> findByEmail(String name);

    List<User> findByOrganisationId(Long organisationId);

    List<User> findByOrganisation_IdAndDeletedFalse(Long organisationId);

    Optional<User> findByIdAndOrganisation_Id(Long userId, Long organisationId);

    boolean existsByIdAndOrganisation_Id(Long id, Long organisationId);
    
    // Optional<User> findByRole(String role);
}
