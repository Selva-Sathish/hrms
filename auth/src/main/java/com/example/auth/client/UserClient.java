package com.example.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.auth.common.ApiResponse;
import com.example.auth.config.user.UserFeignConfig;
import com.example.auth.dto.user.UserCreateRequest;
import com.example.auth.dto.user.UserResponse;


@FeignClient(
    name = "user-service",
    url = "${services.user-service.url}",
    configuration = UserFeignConfig.class
)
public interface UserClient {
    @PostMapping("/users")
    ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest userRequest);
}
