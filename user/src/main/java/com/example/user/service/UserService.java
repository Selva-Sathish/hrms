package com.example.user.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.user.dto.user.ManagerUserResponse;
import com.example.user.dto.user.UserPatchRequest;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.mapper.UserMapper;
import com.example.user.models.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.utils.SecurityUtils;

import jakarta.transaction.Transactional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final UserMapper userMapper;
    
    public UserService(
        UserRepository userRepository,
        SecurityUtils securityUtils,
        UserMapper userMapper
    ){
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.userMapper = userMapper;
    }
    
    public User getByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("email is not registered"));
    }

    public List<User> getUsers(){
        Long organisationId = securityUtils.getCurrentOrganisationId();
        List<User> users =  userRepository.findByOrganisation_IdAndDeletedFalse(organisationId);
        return users;
    }

    
    public User getUserId(Long userId){
        Long organisationId = securityUtils.getCurrentOrganisationId();
        User user = userRepository
                        .findByIdAndOrganisation_Id(userId, organisationId)
                        .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        
        return user;
    }


    public ManagerUserResponse patchUpdate(Long id, UserPatchRequest request) {
        User user = getUserId(id);
        userMapper.updateUserFromPatchDto(request, user);
        userRepository.save(user);
        return userMapper.toManagerResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        Long organisationId = securityUtils.getCurrentOrganisationId();
        if(!userRepository.existsByIdAndOrganisation_Id(id, organisationId)){
            throw new ResourceNotFoundException("user not found");
        }   
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        user.setDeleted(true);
        user.setDeletedAt(Instant.now());
        userRepository.save(user);
    }

}
