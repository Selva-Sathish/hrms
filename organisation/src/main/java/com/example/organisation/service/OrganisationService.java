package com.example.organisation.service;

import org.springframework.stereotype.Service;
import com.example.organisation.dto.organisation.OrganisationRequest;
import com.example.organisation.dto.organisation.OrganisationResponse;
import com.example.organisation.exception.AccessDeniedException;
import com.example.organisation.exception.ResourceAlreadyExists;
import com.example.organisation.exception.ResourceNotFoundException;
import com.example.organisation.mapper.OrganisationMapper;
import com.example.organisation.models.Organisation;
import com.example.organisation.repository.OrganisationRepository;
import com.example.organisation.security.CurrentUser;

@Service
public class OrganisationService {
    private final OrganisationRepository organisationRepository;
    // private final SecurityUtils securityUtils;
    private final OrganisationMapper organisationMapper;
    private final CurrentUser currentUser;

    public OrganisationService(
        OrganisationRepository organisationRepository,
        OrganisationMapper organisationMapper,
        CurrentUser currentUser
    ){
        this.organisationRepository = organisationRepository;
        this.currentUser = currentUser;
        this.organisationMapper = organisationMapper;
    }


    public OrganisationResponse createOrganisation(String name){
        if(organisationRepository.existsByName(name)){
            throw new ResourceAlreadyExists("unable to create the organisation with the provided details.");
        }

        Organisation org = new Organisation();
        org.setName(name);
        Organisation organisation = organisationRepository.save(org);
        return organisationMapper.toOrganisationResponse(organisation);
    }

    public Organisation getById(Long id){
        if(currentUser.getOrganisationId() != id){
            throw new AccessDeniedException("you don't have access to this organisation");
        }
        Organisation organisation = organisationRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
        
        return organisation;
    }

    public OrganisationResponse getByName(String name){
        Long organisationId = currentUser.getOrganisationId();
        
        Organisation organisation = organisationRepository
            .findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
        
        if(organisationId != organisation.getId()){
            throw new AccessDeniedException("you don't have access to this organisation");
        }

        return organisationMapper.toOrganisationResponse(organisation);
    }


    public void deleteById(Long id){
        if(currentUser.getOrganisationId() != id){
            throw new AccessDeniedException("you don't have access to this organisation");
        }
        Organisation organisation = organisationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
        
        organisationRepository.delete(organisation);
    }


    public OrganisationResponse updateOrganisation(Long id, OrganisationRequest request) {
        if(currentUser.getOrganisationId() != id){
            throw new AccessDeniedException("you don't have access to this organisation");
        }

        Organisation organisation = organisationRepository.findById(id)
            .orElseThrow(
                () ->  new ResourceNotFoundException("organisation not found")
            );
        
        if(organisationRepository.existsByName(request.getName())){
            throw new ResourceAlreadyExists("try different name");
        }

        organisationMapper.toEntity(organisation, request);
        organisation = organisationRepository.save(organisation);
        return organisationMapper.toOrganisationResponse(organisation);
    }
}
