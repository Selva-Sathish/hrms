package com.example.auth.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.user.UserCreateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserCreateRequest toCreateRequest(RegisterRequest request);
}
