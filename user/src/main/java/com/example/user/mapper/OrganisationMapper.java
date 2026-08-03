package com.example.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.user.dto.organisation.OrganisationRequest;
import com.example.user.models.Organisation;

@Mapper(componentModel = "spring")
public interface OrganisationMapper{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "user", ignore = true)
    void toEntity(@MappingTarget Organisation organisation, OrganisationRequest request);


}