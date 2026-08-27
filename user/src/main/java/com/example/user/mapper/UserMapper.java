package com.example.user.mapper;

import org.mapstruct.Mapper;

import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User toEntity(UserCreateRequest request);
}
