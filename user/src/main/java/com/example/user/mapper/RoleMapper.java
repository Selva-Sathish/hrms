package com.example.user.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.user.dto.role.RoleRequest;
import com.example.user.dto.role.RoleResponse;
import com.example.user.models.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    List<RoleResponse> toRoleResponseList(List<Role> role);
    
    RoleResponse toRoleResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organisation", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void toEntity(@MappingTarget Role role, RoleRequest request);
}
