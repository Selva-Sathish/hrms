package com.example.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.common.ApiResponse;
import com.example.user.dto.user.AdminCreateUserRequest;
import com.example.user.dto.user.AdminUserResponse;
import com.example.user.dto.user.UserPatchRequest;
import com.example.user.service.AdminUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    
    private final AdminUserService adminUserService;

    public AdminUserController(
        AdminUserService adminUserService
    ){
        this.adminUserService = adminUserService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createUser(@Valid @RequestBody AdminCreateUserRequest request){
        adminUserService.createUser(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ApiResponse<>(true, "user created successfully", null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long id){
        adminUserService.deleteUser(id);
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(new ApiResponse<>(true, "user deleted", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UserPatchRequest request
    ){
        AdminUserResponse response = adminUserService.updateUser(id, request);
        return ResponseEntity
            .ok()
            .body(new ApiResponse<>(true, "user updated successfully", response));
    }
}
