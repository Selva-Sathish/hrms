package com.example.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.auth.dto.role.RoleRequest;
import com.example.auth.dto.role.RoleResponse;
import com.example.auth.exception.BadRequestException;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.mapper.RoleMapper;
import com.example.auth.models.Organisation;
import com.example.auth.models.Role;
import com.example.auth.repository.RoleRepository;
import com.example.auth.security.CurrentUser;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final CurrentUser currentUser;
    private final OrganisationService organisationService;
    public RoleService(
        RoleRepository roleRepository,
        RoleMapper roleMapper,
        CurrentUser currentUser,
        OrganisationService organisationService
    ) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.currentUser = currentUser;
        this.organisationService = organisationService;
    }

    public Role getRoleById(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
        
        if(currentUser.getOrganisationId() != role.getOrganisation().getId()){
            throw new BadRequestException("sorry you can't access it.");
        }

        return role;
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
    }

    public RoleResponse createRole(RoleRequest request) {
        Long organisationId = currentUser.getOrganisationId();
        Role role = roleMapper.toEntity(request);
        Organisation organisation = organisationService.getById(organisationId);
        role.setOrganisation(organisation);
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getRoles() {
        Long organisationId = currentUser.getOrganisationId();
        List<Role> roles = roleRepository.findByOrganisation_Id(organisationId);
        List<RoleResponse> response = roleMapper.toRoleResponseList(roles);
        return response;
    }

    public RoleResponse updateRole(Long roleId, RoleRequest request){
        Long organisationId = currentUser.getOrganisationId();
        Role role = roleRepository.findByIdAndOrganisation_Id(roleId, organisationId)
            .orElseThrow(() -> new BadRequestException("role not found"));
        
        roleMapper.updateOrganisation(role, request);
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }  

    public void deleteRole(Long roleId){
        Long organisationId = currentUser.getOrganisationId();
        Role role = roleRepository.findByIdAndOrganisation_Id(roleId, organisationId)
            .orElseThrow(() -> new BadRequestException("role not found"));
        roleRepository.delete(role);
    }
}
