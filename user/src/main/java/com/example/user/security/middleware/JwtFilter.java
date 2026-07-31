package com.example.user.security.middleware;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.user.security.dao.UserDetailPrinciple;
import com.example.user.security.service.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter{

    private final JwtService jwtUtils;
    
    public JwtFilter(JwtService jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        Cookie[] cookies = request.getCookies();

        if(cookies == null){
            filterChain.doFilter(request, response);
            return;
        }
        
        Optional<Cookie> cookie = Arrays
                .stream(cookies)
                .filter(c -> "access_token".equals(c.getName()))
                .findFirst();
        
        if(cookie.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String authToken = cookie.get().getValue();
            Long userId = jwtUtils.getUserId(authToken);
            Long organisation = jwtUtils.getOrganisation(authToken);
            String role = jwtUtils.getRole(authToken);
            if(SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetailPrinciple principle = new UserDetailPrinciple(
                    userId, organisation, role
                );
                
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    principle,
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(role))
                );
                
                SecurityContextHolder.getContext().setAuthentication(token);

            }
            
        } catch (JwtException e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or token expired");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
    
}   
