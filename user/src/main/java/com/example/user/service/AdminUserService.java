package com.example.user.service;

import org.springframework.stereotype.Service;

import com.example.user.dto.event.UserCreateEvent;
import com.example.user.dto.user.AdminCreateUserRequest;

import com.example.user.kafka.producer.UserProducer;
import com.example.user.mapper.UserMapper;
import com.example.user.models.Organisation;
import com.example.user.models.Role;
import com.example.user.models.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.utils.SecurityUtils;

import jakarta.transaction.Transactional;

@Service
public class AdminUserService {

    private final SecurityUtils securityUtils;
    private final RoleService roleService;
    private final OrganisationService organisationService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserProducer userProducer;
    private final UserValidationService userValidationService;

    public AdminUserService(
        SecurityUtils securityUtils,
        RoleService roleService,
        OrganisationService organisationService,
        UserMapper userMapper,
        UserRepository userRepository,
        UserProducer userProducer,
        UserValidationService userValidationService
    ){
        this.securityUtils = securityUtils;
        this.roleService = roleService;
        this.organisationService = organisationService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.userProducer = userProducer;
        this.userValidationService = userValidationService;

    }
    
    @Transactional
    public void createUser(AdminCreateUserRequest request){
        userValidationService.validateEmailNotExists(request.getEmail());
        Long organisationId = securityUtils.getCurrentOrganisationId();
        Long roleId = request.getRoleId();
        
        Role role = roleService.getRoleById(roleId);
        Organisation organisation = organisationService.getById(organisationId);
        
        User user = userMapper.adminUserCreateUser(request);
        user.setOrganisation(organisation);
        user.setRole(role);
        user = userRepository.save(user);
        UserCreateEvent event =  userMapper.toUserCreateEvent(user);
        userProducer.publishUserCreated(event);
    }

    
}
