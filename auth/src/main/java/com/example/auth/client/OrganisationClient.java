package com.example.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.auth.dto.organisation.OrganisationRequest;
import com.example.auth.dto.organisation.OrganisationResponse;

@FeignClient(
    name = "organisation-service",
    url = "${services.organisation-service.url}"
)
public interface OrganisationClient {
    
    @PostMapping("/organisations")
    OrganisationResponse createOrganisation(
        @RequestBody OrganisationRequest request 
    );
}