package com.example.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.common.ApiResponse;
import com.example.auth.dto.organisation.OrganisationRequest;
import com.example.auth.dto.organisation.OrganisationResponse;
import com.example.auth.service.OrganisationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/organisations")
public class OrganisationController {

    private final OrganisationService organisationService;

    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrganisation(
        @Valid @RequestBody OrganisationRequest request
    ) {
        OrganisationResponse response = organisationService.createOrganisation(request.getName());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ApiResponse<>(
                    true,
                    "organisation created successfully",
                    response
                )
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateOrganisation(
        @PathVariable Long id,
        @Valid @RequestBody OrganisationRequest request
    ) {
        OrganisationResponse response = organisationService.updateOrganisation(id, request);
        return ResponseEntity.ok().body(
            new ApiResponse<>(true, "organisation update successfully", response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteOrganisation(@PathVariable Long id) {
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
