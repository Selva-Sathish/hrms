package com.example.auth.service;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class JwtInterceptor implements RequestInterceptor {

    private final JwtService jwtService;
    private final String audience;
    private final String scope;

    public JwtInterceptor(
        JwtService jwtService,
        String audience,
        String scope
    ){
        this.jwtService = jwtService;
        this.audience = audience;
        this.scope = scope;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = jwtService.generateToken(audience, scope);
        System.out.println("Feign JWT generated");
        System.out.println("Audience: " + audience);
        System.out.println("Scope: " + scope);
        System.out.println("Token: " + token);


        template.header("Authorization", "Bearer " + token);
    }
    
}
