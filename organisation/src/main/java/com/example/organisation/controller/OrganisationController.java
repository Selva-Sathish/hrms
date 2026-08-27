package com.example.organisation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.organisation.common.ApiResponse;
import com.example.organisation.dto.organisation.OrganisationRequest;
import com.example.organisation.dto.organisation.OrganisationResponse;
import com.example.organisation.security.CurrentUser;
import com.example.organisation.service.OrganisationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organisations")
public class OrganisationController {
    
    private final OrganisationService organisationService;
    public OrganisationController(
        OrganisationService organisationService,
        CurrentUser currentUser
    ){
        this.organisationService = organisationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> createOrganisation(
        @Valid @RequestBody OrganisationRequest request
    ){
        OrganisationResponse response = organisationService.createOrganisation(request.getName());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ApiResponse<>(
                    true, 
                    "organisation created successfully",
                    response
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateOrganisation(
        @PathVariable Long id,
        @Valid @RequestBody OrganisationRequest request
    ){
        OrganisationResponse response = organisationService.updateOrganisation(id, request);
        return ResponseEntity.ok().body(
            new ApiResponse<>(true, "organisation update successfully", response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteOrganisation(@PathVariable Long id){
        organisationService.deleteById(id);
        return ResponseEntity
            .ok()
            .body(
                new ApiResponse<>(
                    true,
                    "organisation deleted successfully",
                    null
                )
            );
    }
}
