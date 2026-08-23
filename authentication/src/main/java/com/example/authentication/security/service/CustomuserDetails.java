package com.example.authentication.security.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.authentication.exception.ResourceNotFoundException;
// import com.example.authentication.models.User;
// import com.example.authentication.repository.UserRepository;
// import com.example.authentication.security.dao.UserPrinciple;

// @Service
// public class CustomuserDetails implements UserDetailsService {
    // private final UserRepository userRepository;

    // public CustomuserDetails(UserRepository userRepository){
    //     this.userRepository = userRepository;
    // }
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     User user = userRepository.findByUsername(username).orElseThrow(
    //         () -> new ResourceNotFoundException("user not found")
    //     );
    //     return new UserPrinciple(user);
    // }
    
// }
