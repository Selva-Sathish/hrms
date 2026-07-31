package com.example.user.service;

import org.springframework.stereotype.Service;

import com.example.user.exception.ResourceAlreadyExists;
import com.example.user.repository.UserRepository;

@Service
public class UserValidationService {
    
    private UserRepository userRepository;

    public UserValidationService(
        UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    public void validateEmailNotExists(String name){
        if(userRepository.existsByEmail(name)){
            throw new ResourceAlreadyExists("email already exists");
        }    
    }
}
