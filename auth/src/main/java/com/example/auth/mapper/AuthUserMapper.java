package com.example.auth.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.auth.dto.user.UserResponse;
import com.example.auth.models.AuthUser;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "id", ignore = true)
    AuthUser toAuthUser(UserResponse userResponse);
}
