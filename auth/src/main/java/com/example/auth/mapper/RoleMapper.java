package com.example.auth.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.auth.dto.role.RoleRequest;
import com.example.auth.dto.role.RoleResponse;
import com.example.auth.models.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    List<RoleResponse> toRoleResponseList(List<Role> role);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "organisation", ignore = true)
    Role toEntity(RoleRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrganisation(@MappingTarget Role role, RoleRequest request);
}
