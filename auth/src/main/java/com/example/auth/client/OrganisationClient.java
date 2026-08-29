package com.example.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.auth.common.ApiResponse;
import com.example.auth.config.organisation.OrganisationFeignConfig;
import com.example.auth.dto.organisation.OrganisationRequest;
import com.example.auth.dto.organisation.OrganisationResponse;

@FeignClient(
    name = "organisation-service",
    url = "${services.organisation-service.url}",
    configuration = OrganisationFeignConfig.class
)
public interface OrganisationClient {
    
    @PostMapping("/organisations")
    ApiResponse<OrganisationResponse> createOrganisation(
        @RequestBody OrganisationRequest request 
    );
}