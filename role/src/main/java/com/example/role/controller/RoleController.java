package com.example.role.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.role.common.ApiResponse;
import com.example.role.dto.role.RoleRequest;
import com.example.role.dto.role.RoleResponse;
import com.example.role.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/roles")
public class RoleController {
    
    private final RoleService roleService;
    public RoleController(
        RoleService roleService
    ){
        this.roleService = roleService;
    }

    // @GetMapping
    // public ResponseEntity<ApiResponse<?>> getAllRoles() {
    //     Long organisationId = securityUtils.getCurrentOrganisationId();
    //     List<RoleResponse> roles = roleService.getRoles(organisationId);
    //     return ResponseEntity
    //         .ok()
    //         .body(new ApiResponse<>(true, "role fetched successfully", roles));
    // }

    // @PostMapping
    // public ResponseEntity<ApiResponse<RoleResponse>> createRole(@Valid @RequestBody RoleRequest request){
    //     RoleResponse response = roleService.createRole(request.getName());
    //     return ResponseEntity
    //             .status(HttpStatus.CREATED)
    //             .body(
    //                 new ApiResponse<>(
    //                     true,
    //                     "role created", 
    //                     response
    //                 )
    //             );
    // }

    // @PatchMapping("/{id}")
    // public ResponseEntity<ApiResponse<?>> updateRole(
    //     @PathVariable Long id, 
    //     @Valid @RequestBody RoleRequest request
    // ){
    //     RoleResponse response = roleService.updateRole(id, request);
    //     return ResponseEntity
    //         .ok()
    //         .body(
    //             new ApiResponse<>(true, "role updated successfully", response)
    //         );
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<ApiResponse<?>> deleteRole(
    //     @PathVariable Long id
    // ){
    //     roleService.deleteRole(id);
    //     return ResponseEntity
    //         .status(HttpStatus.NO_CONTENT)
    //         .body(new ApiResponse<>(true, "role is deleted", null));
    // }
    
}
