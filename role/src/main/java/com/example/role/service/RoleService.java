package com.example.role.service;

import org.springframework.stereotype.Service;
import com.example.role.exception.ResourceNotFoundException;
import com.example.role.mapper.RoleMapper;
import com.example.role.models.Role;
import com.example.role.repository.RoleRepository;


@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    
    private final static String PREFIX = "ROLE_";

    public RoleService(
        RoleRepository roleRepository,
        RoleMapper roleMapper
    ){
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    // public Role createRole(String name, Organisation organisation){
    //     Role role = new Role();
    //     role.setName(PREFIX + name.toUpperCase());
    //     role.setOrganisation(organisation);
    //     roleRepository.save(role);
    //     return role;
    // }
    
    // public Role createRole(String name, Long organisationId){
    //     Organisation organisation = organisationService.getById(organisationId);
    //     String roleName = PREFIX + name.toUpperCase();
    //     if(roleRepository.existsByNameAndOrganisation_Id(roleName, organisationId)){
    //         throw new ResourceAlreadyExists("role already exists");
    //     }
    //     Role role = new Role();
    //     role.setName(roleName);
    //     role.setOrganisation(organisation);
    //     return role;
    // }

    public Role getRoleById(Long id){
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
    }

    public Role getRoleByName(String name){
        return roleRepository.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("role not found"));
    }

    // public List<RoleResponse> getRoles(Long organisationId){
    //     List<Role> roles = roleRepository.findByOrganisation_Id(organisationId);
    //     return roleMapper.toRoleResponseList(roles);
    // }

    // public RoleResponse createRole(String name) {
    //     Long organisationId = securityUtils.getCurrentOrganisationId();
        
    //     Role role = createRole(name, organisationId);
    //     RoleResponse response = roleMapper.toRoleResponse(role);
    //     return response;
    // }

    // @Transactional
    // public void deleteRole(Long id) {
    //     Long organisationId = securityUtils.getCurrentOrganisationId();
    //     Role role = roleRepository.findByIdAndOrganisation_Id(id, organisationId)
    //         .orElseThrow(() -> new ResourceNotFoundException("role is not found"));
        
    //     roleRepository.delete(role);
    // }


    // public RoleResponse updateRole(Long id, RoleRequest request) {
    //     Long organisationId = securityUtils.getCurrentOrganisationId();
    //     Role role = roleRepository.findByIdAndOrganisation_Id(id, organisationId)
    //         .orElseThrow(() -> new ResourceNotFoundException("role not found"));
        
    //     // roleMapper.toEntity(role, request);
    //     role.setName(PREFIX + request.getName());
        
    //     roleRepository.save(role);
    //     return roleMapper.toRoleResponse(role);

    // }

}
