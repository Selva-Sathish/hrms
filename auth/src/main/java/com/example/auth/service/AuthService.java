package com.example.auth.service;

import org.springframework.stereotype.Service;

import com.example.auth.client.OrganisationClient;
import com.example.auth.client.UserClient;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.organisation.OrganisationRequest;
import com.example.auth.dto.organisation.OrganisationResponse;
import com.example.auth.dto.user.UserCreateRequest;
import com.example.auth.dto.user.UserResponse;
import com.example.auth.mapper.AuthUserMapper;
import com.example.auth.mapper.OrganisationMapper;
import com.example.auth.mapper.UserMapper;
import com.example.auth.models.AuthUser;
import com.example.auth.repository.AuthUserRepo;

@Service
public class AuthService {

    private final AuthUserRepo authUserRepo;
    private final OrganisationClient organisationClient;
    private final OrganisationMapper organisationMapper;
    private final UserMapper userMapper;
    private final UserClient userClient;
    private final AuthUserMapper authUserMapper;
    
    public AuthService(
        AuthUserRepo authUserRepo,
        OrganisationClient organisationClient,
        UserClient userClient,
        OrganisationMapper organisationMapper,
        UserMapper userMapper,
        AuthUserMapper authUserMapper
    ){
        this.authUserRepo = authUserRepo;
        this.organisationClient = organisationClient;
        this.organisationMapper = organisationMapper;
        this.userMapper = userMapper;
        this.userClient = userClient;
        this.authUserMapper = authUserMapper;
    }

    public void registerUser(RegisterRequest request) {
        OrganisationRequest orgRequest = organisationMapper.toOrganisationRequest(request);
        OrganisationResponse orgResponse =  organisationClient.createOrganisation(orgRequest);  
        
        UserCreateRequest userRequest = userMapper.toCreateRequest(request);
        userRequest.setOrganisationId(orgResponse.getId());
        
        UserResponse userResponse = userClient.createUser(userRequest);

        AuthUser authUser = authUserMapper.toAuthUser(userResponse);
        
        authUserRepo.save(authUser);
    }
    
}
