package com.example.auth.config.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.auth.service.JwtInterceptor;
import com.example.auth.service.JwtService;

@Configuration
public class UserFeignConfig {
    
    @Bean
    public JwtInterceptor userJwtInterceptor(JwtService jwtService){
        return new JwtInterceptor(jwtService, "user-service", "user:create");
    }
}
