package com.example.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.user.dto.role.RoleResponse;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.mapper.RoleMapper;
import com.example.user.models.Organisation;
import com.example.user.models.Role;
import com.example.user.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final OrganisationService organisationService;
    private final RoleMapper roleMapper;
    
    private final static String PREFIX = "ROlE_";

    public RoleService(
        RoleRepository roleRepository,
        OrganisationService organisationsService,
        RoleMapper roleMapper
    ){
        this.roleRepository = roleRepository;
        this.organisationService = organisationsService;
        this.roleMapper = roleMapper;
    }

    public Role createRole(String name, Organisation organisation){
        Role role = new Role();
        role.setName(PREFIX + name.toUpperCase());
        role.setOrganisation(organisation);
        roleRepository.save(role);
        return role;
    }
    
    public Role creatRole(String name, Long organisationId){
        Organisation organisation = organisationService.getById(organisationId);
        Role role = new Role();
        role.setName(PREFIX + name.toUpperCase());
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

}
