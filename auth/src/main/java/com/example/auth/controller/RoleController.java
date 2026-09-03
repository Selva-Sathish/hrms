package com.example.auth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.auth.common.ApiResponse;
import com.example.auth.dto.role.RoleRequest;
import com.example.auth.dto.role.RoleResponse;
import com.example.auth.service.RoleService;


@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(
        RoleService roleService
    ) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getRoles(){
        List<RoleResponse> response = roleService.getRoles();
        return ResponseEntity
            .ok()
            .body(
                new ApiResponse<>(true, "role fetched successfully", response)
            );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createRole(RoleRequest request){
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ApiResponse<>(
                    true,
                    "role created successfully",
                    response
                )
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateRole(
        @PathVariable Long roleId, 
        @RequestBody RoleRequest request
    ){

        RoleResponse response = roleService.updateRole(roleId, request);
        return ResponseEntity
            .ok()
            .body(
                new ApiResponse<>(
                    true,
                    "role updated successfully",
                    response
                )
            );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>>  deleteRole(
        @PathVariable Long roleId
    ){
        roleService.deleteRole(roleId);
        return ResponseEntity
            .ok()
            .body(
                new ApiResponse<>(
                    true,
                    "role deleted sucessfully",
                    null
                )
            ); 
    }
}
