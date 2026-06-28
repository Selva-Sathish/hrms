package com.example.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.common.ApiResponse;
import com.example.user.dto.role.RoleResponse;
import com.example.user.security.utils.SecurityUtils;
import com.example.user.service.RoleService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/roles")
public class RoleController {
    
    private final RoleService roleService;
    private final SecurityUtils securityUtils;

    
    public RoleController(
        RoleService roleService,
        SecurityUtils securityUtils
    ){
        this.roleService = roleService;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllRoles() {
        Long organisationId = securityUtils.getCurrentOrganisationId();
        List<RoleResponse> roles = roleService.getRoles(organisationId);
        return ResponseEntity
            .ok()
            .body(new ApiResponse<>(true, "role fetched successfully", roles));
    }
    
}
