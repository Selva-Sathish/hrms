package com.example.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.user.exception.BadRequestException;
import com.example.user.exception.ResourceAlreadyExists;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.models.Organisation;
import com.example.user.repository.OrganisationRepository;
import com.example.user.security.dao.UserDetailPrinciple;

@Service
public class OrganisationService {
    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository){
        this.organisationRepository = organisationRepository;
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
}
