package com.example.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.common.ApiResponse;
import com.example.user.dto.user.AdminCreateUserRequest;
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
}
