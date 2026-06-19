package com.example.user.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.user.exception.ResourceAlreadyExists;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.models.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.utils.SecurityUtils;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    
    public UserService(
        UserRepository userRepository,
        SecurityUtils securityUtils
    ){
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }
    

    public void validEmailNotExists(String name){
        if(userRepository.existsByEmail(name)){
            throw new ResourceAlreadyExists("email already exists");
        }    
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("email is not registered"));
    }

    public List<User> getUsers(){
        Long organisationId = securityUtils.getCurrentOrganisationId();
        List<User> users =  userRepository.findByOrganisation_IdAndIsDeletedFalse(organisationId);
        return users;
    }

    
    public User getUserId(Long userId){
        Long organisationId = securityUtils.getCurrentOrganisationId();
        User user = userRepository
                        .findByIdAndOrganisationId(userId, organisationId)
                        .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        
        return user;
    }

}
