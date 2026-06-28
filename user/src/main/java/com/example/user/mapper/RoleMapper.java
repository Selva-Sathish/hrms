package com.example.user.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.user.dto.role.RoleResponse;
import com.example.user.models.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    List<RoleResponse> toRoleResponseList(List<Role> role);
}
