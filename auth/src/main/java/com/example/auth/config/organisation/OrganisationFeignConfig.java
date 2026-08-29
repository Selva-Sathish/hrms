package com.example.auth.config.organisation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.auth.service.JwtInterceptor;
import com.example.auth.service.JwtService;

@Configuration
public class OrganisationFeignConfig {
    @Bean
    public JwtInterceptor OrganisationJwtInterceptor(JwtService jwtService){
        return new JwtInterceptor(
            jwtService, 
            "organisation-servie", 
            "organisation-create"
        );
    }    
}
