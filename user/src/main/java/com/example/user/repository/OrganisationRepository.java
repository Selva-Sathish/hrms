package com.example.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.models.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long>{
    Optional<Organisation> findByName(String name);

    boolean existsByName(String name);
}
