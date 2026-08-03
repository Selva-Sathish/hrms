package com.example.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.common.ApiResponse;
import com.example.user.dto.organisation.OrganisationRequest;
import com.example.user.service.OrganisationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/organisation")
public class OrganisationController {
    
    private final OrganisationService organisationService;

    public OrganisationController(
        OrganisationService organisationService
    ){
        this.organisationService = organisationService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateOrganisation(
        @PathVariable Long id,
        @Valid @RequestBody OrganisationRequest request
    ){
        organisationService.updateOrganisation(id, request);
        return ResponseEntity.ok().body(null);
    }
}
