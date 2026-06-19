package com.example.user.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.user.security.dao.UserDetailPrinciple;

@Component
public class SecurityUtils {
    public Long getCurrentUserId(){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        UserDetailPrinciple principle =  (UserDetailPrinciple) auth.getPrincipal();
        return principle.getUserId();
    }

    public Long getCurrentOrganisationId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailPrinciple principle = (UserDetailPrinciple) auth.getPrincipal();
        return principle.getOrganisationId();
    }
}
