package com.example.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.user.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByName(String name);
    
    List<Role> findByOrganisation_Id(Long organisationId);
}
