package com.example.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.example.user.dto.user.CreateUserRequest;
import com.example.user.dto.event.UserCreateEvent;
import com.example.user.dto.user.AdminCreateUserRequest;
import com.example.user.dto.user.AdminUserResponse;
import com.example.user.dto.user.ManagerUserResponse;
import com.example.user.dto.user.UserPatchRequest;
import com.example.user.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "organisation", ignore = true)
    User toEntity(CreateUserRequest request);

    @Mapping(source = "organisation.id", target = "organisationId")
    @Mapping(source = "organisation.name", target = "organisationName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    AdminUserResponse toAdminResponse(User user);

    @Mapping(source = "organisation.id", target = "organisationId")
    @Mapping(source = "organisation.name", target = "organisationName")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    ManagerUserResponse toManagerResponse(User user);

    @BeanMapping(
        nullValuePropertyMappingStrategy =
            NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organisation", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "lockedAt", ignore = true)
    @Mapping(target = "failedLoginAttempts", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "verified", ignore = true)
    void updateUserFromPatchDto(UserPatchRequest request, @MappingTarget User user);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    User adminUserCreateUser(AdminCreateUserRequest request);

    UserCreateEvent toUserCreateEvent(User user);
    
    // AdminUserResponse toAdminUserResponse();
}
