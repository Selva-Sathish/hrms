package com.example.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.organisation.OrganisationRequest;

@Mapper(componentModel = "spring")
public interface OrganisationMapper {

    @Mapping(target = "name", source = "organisation")
    OrganisationRequest toOrganisationRequest(RegisterRequest request);
}
