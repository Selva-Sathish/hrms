package com.example.organisation.dto.organisation;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganisationRequest {
    
    @NotBlank
    private String name;
}
