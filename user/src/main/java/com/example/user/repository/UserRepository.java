package com.example.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.user.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    boolean existsByEmail(String name);

    Optional<User> findByEmail(String name);

    List<User> findByOrganisationId(Long organisationId);

    List<User> findByOrganisation_IdAndIsDeletedFalse(Long organisationId);

    Optional<User> findByIdAndOrganisationId(Long userId, Long organisationId);
}
