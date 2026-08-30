package com.example.auth.service;

import org.springframework.stereotype.Service;

import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.mapper.RoleMapper;
import com.example.auth.models.Role;
import com.example.auth.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    private static final String PREFIX = "ROLE_";

    public RoleService(
        RoleRepository roleRepository,
        RoleMapper roleMapper
    ) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
    }
}
