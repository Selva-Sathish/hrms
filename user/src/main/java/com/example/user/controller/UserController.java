package com.example.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.common.ApiResponse;
import com.example.user.dto.user.ManagerUserResponse;
import com.example.user.dto.user.UserPatchRequest;
import com.example.user.models.User;
import com.example.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(
        UserService userService
    ){
        this.userService = userService;
    }
    
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getUsers();    
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUserId(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ManagerUserResponse>> updateUser(@PathVariable Long id, @RequestBody UserPatchRequest request){
        ManagerUserResponse response =  userService.patchUpdate(id, request);
        return ResponseEntity
            .ok()
            .body(new ApiResponse<>(true, "user updated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
        return ResponseEntity
                .ok(new ApiResponse<>(true, "user deleted successfully", null));
    }

    
}
