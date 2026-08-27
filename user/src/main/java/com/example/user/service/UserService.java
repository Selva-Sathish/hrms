package com.example.user.service;

import org.springframework.stereotype.Service;

import com.example.user.dto.UserCreateRequest;
import com.example.user.dto.UserResponse;
import com.example.user.exception.ResourceAlreadyExists;
import com.example.user.mapper.UserMapper;
import com.example.user.models.User;
import com.example.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
        UserRepository userRepository,
        UserMapper userMapper
    ){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void validateEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new ResourceAlreadyExists("try different email");
        }
    }

    public UserResponse createUser(UserCreateRequest request) {
        validateEmail(request.getEmail());
        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    
}
