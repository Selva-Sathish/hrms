package com.example.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.user.dto.organisation.OrganisationRequest;
import com.example.user.exception.BadRequestException;
import com.example.user.exception.ResourceAlreadyExists;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.mapper.OrganisationMapper;
import com.example.user.models.Organisation;
import com.example.user.repository.OrganisationRepository;
import com.example.user.security.dao.UserDetailPrinciple;
import com.example.user.security.utils.SecurityUtils;

@Service
public class OrganisationService {
    private final OrganisationRepository organisationRepository;
    private final SecurityUtils securityUtils;
    private final OrganisationMapper organisationMapper;

    public OrganisationService(
        OrganisationRepository organisationRepository,
        SecurityUtils securityUtils,
        OrganisationMapper organisationMapper
    ){
        this.organisationRepository = organisationRepository;
        this.securityUtils = securityUtils;
        this.organisationMapper = organisationMapper;
    }


    public Organisation createOrganisation(String name){
        if(organisationRepository.existsByName(name)){
            throw new ResourceAlreadyExists("unable to create the organisation with the provided details.");
        }

        Organisation org = new Organisation();
        org.setName(name);
        return organisationRepository.save(org);
    }

    public Organisation getById(Long id){
        Organisation organisation = organisationRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
        
        return organisation;
    }

    public Organisation getByName(String name){
        return organisationRepository
            .findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
    }

    public Long getCurrentOrganisationId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailPrinciple principle =  (UserDetailPrinciple)authentication.getPrincipal();
        return principle.getOrganisationId();
    }

    public void deleteById(Long id){
        Long currUserOrganisation = getCurrentOrganisationId();
        if(currUserOrganisation != id){
            throw new BadRequestException("you can't able to delete this organisation");
        }
        Organisation organisation = organisationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("organisation not found"));
        
        organisationRepository.delete(organisation);
    }


    public void updateOrganisation(Long id, OrganisationRequest request) {
        Long organisationId = securityUtils.getCurrentOrganisationId();
        if(id != organisationId){
            throw new BadRequestException("organisation not found");
        }
        Organisation organisation = organisationRepository.findById(organisationId)
            .orElseThrow(
                () ->  new ResourceNotFoundException("organisation not found")
            );
        
        organisationMapper.toEntity(organisation, request);
        organisationRepository.save(organisation);
    }
}
