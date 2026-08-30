package com.example.auth.service;

import org.springframework.stereotype.Service;

import com.example.auth.dto.organisation.OrganisationRequest;
import com.example.auth.dto.organisation.OrganisationResponse;
import com.example.auth.exception.ResourceAlreadyExists;
import com.example.auth.exception.ResourceNotFoundException;
import com.example.auth.mapper.OrganisationMapper;
import com.example.auth.models.Organisation;
import com.example.auth.repository.OrganisationRepository;
import com.example.auth.security.CurrentUser;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;
    private final CurrentUser currentUser;

    public OrganisationService(
        OrganisationRepository organisationRepository,
        OrganisationMapper organisationMapper,
        CurrentUser currentUser
    ) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
        this.currentUser = currentUser;
    }

    public OrganisationResponse createOrganisation(String name) {
        if (organisationRepository.existsByName(name)) {
            throw new ResourceAlreadyExists("unable to create the organisation with the provided details.");
        }
        Organisation org = new Organisation();
        org.setName(name);
        Organisation organisation = organisationRepository.save(org);
        return organisationMapper.toOrganisationResponse(organisation);
    }

    public Organisation getById(Long id) {
        return organisationRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
    }

    public OrganisationResponse getByName(String name) {
        Organisation organisation = organisationRepository
            .findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
        return organisationMapper.toOrganisationResponse(organisation);
    }

    public void deleteById(Long id) {
        Organisation organisation = organisationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
        organisationRepository.delete(organisation);
    }

    public OrganisationResponse updateOrganisation(Long id, OrganisationRequest request) {
        Organisation organisation = organisationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));

        if (organisationRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExists("try different name");
        }

        organisationMapper.toEntity(organisation, request);
        organisation = organisationRepository.save(organisation);
        return organisationMapper.toOrganisationResponse(organisation);
    }
}
