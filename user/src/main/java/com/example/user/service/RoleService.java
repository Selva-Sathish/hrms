package com.example.user.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.user.common.ApiResponse;
import com.example.user.dto.role.RoleRequest;
import com.example.user.dto.role.RoleResponse;
import com.example.user.exception.ResourceAlreadyExists;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.mapper.RoleMapper;
import com.example.user.models.Organisation;
import com.example.user.models.Role;
import com.example.user.repository.RoleRepository;
import com.example.user.security.utils.SecurityUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final OrganisationService organisationService;
    private final RoleMapper roleMapper;
    private final SecurityUtils securityUtils;
    
    private final static String PREFIX = "ROLE_";

    public RoleService(
        RoleRepository roleRepository,
        OrganisationService organisationsService,
        RoleMapper roleMapper,
        SecurityUtils securityUtils
    ){
        this.roleRepository = roleRepository;
        this.organisationService = organisationsService;
        this.roleMapper = roleMapper;
        this.securityUtils = securityUtils;
    }

    public Role createRole(String name, Organisation organisation){
        Role role = new Role();
        role.setName(PREFIX + name.toUpperCase());
        role.setOrganisation(organisation);
        roleRepository.save(role);
        return role;
    }
    
    public Role createRole(String name, Long organisationId){
        Organisation organisation = organisationService.getById(organisationId);
        String roleName = PREFIX + name.toUpperCase();
        if(roleRepository.existsByNameAndOrganisation_Id(roleName, organisationId)){
            throw new ResourceAlreadyExists("role already exists");
        }
        Role role = new Role();
        role.setName(roleName);
        role.setOrganisation(organisation);
        return role;
    }

    public Role getRoleById(Long id){
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
    }

    public Role getRoleByName(String name){
        return roleRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
    }

    public List<RoleResponse> getRoles(Long organisationId){
        List<Role> roles = roleRepository.findByOrganisation_Id(organisationId);
        return roleMapper.toRoleResponseList(roles);
    }

    public RoleResponse createRole(String name) {
        Long organisationId = securityUtils.getCurrentOrganisationId();
        
        Role role = createRole(name, organisationId);
        RoleResponse response = roleMapper.toRoleResponse(role);
        return response;
    }

    @Transactional
    public void deleteRole(Long id) {
        Long organisationId = securityUtils.getCurrentOrganisationId();
        Role role = roleRepository.findByIdAndOrganisation_Id(id, organisationId)
            .orElseThrow(() -> new ResourceNotFoundException("role is not found"));
        
        roleRepository.delete(role);
    }


    public ResponseEntity<ApiResponse<?>> updateRole(Long id, RoleRequest request) {
        Long organisationId = securityUtils.getCurrentOrganisationId();
        Role role = roleRepository.findByIdAndOrganisation_Id(id, organisationId)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
        
        Role updateRole = roleMapper.toEntity(role, request);
        RoleResponse response = roleMapper.toRoleResponse(updateRole);
        return ResponseEntity
            .ok()
            .body(
                new ApiResponse<>(true, "role updated successfully", response)
            );
    }

}
