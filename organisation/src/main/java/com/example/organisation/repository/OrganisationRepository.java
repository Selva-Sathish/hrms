package com.example.organisation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.organisation.models.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long>{
    Optional<Organisation> findByName(String name);

    boolean existsByName(String name);
}
