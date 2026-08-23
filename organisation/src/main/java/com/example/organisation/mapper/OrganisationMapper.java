package com.example.organisation.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.organisation.dto.organisation.OrganisationRequest;
import com.example.organisation.models.Organisation;

@Mapper(componentModel = "spring")
public interface OrganisationMapper{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void toEntity(@MappingTarget Organisation organisation, OrganisationRequest request);


}