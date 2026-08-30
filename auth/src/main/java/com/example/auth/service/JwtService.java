package com.example.auth.service;

import java.util.Date;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

import com.example.auth.models.AuthUser;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService{

    private final PrivateKey privateKey;
    private String token;
    Instant expiresAt;

    public JwtService(PrivateKey privateKey){
        this.privateKey = privateKey;
    }
    
 
    private Date getAccessTokenExpiry(){
        return Date.from(Instant.now().plus(15, ChronoUnit.MINUTES));
    }

    public Date getRefreshTokenExpiry(){
        return Date.from(Instant.now().plus(90, ChronoUnit.DAYS));
    }
    
    public String generateRefreshToken(AuthUser user){
        token = Jwts
            .builder()
            .subject(user.getEmail())
            .claim("org-id", user.getOrganisationId())
            .claim("type", "refresh_token")
            .expiration(getRefreshTokenExpiry())
            .signWith(privateKey)
            .compact();
        return token;
    } 

    public String generateAccessToken(AuthUser user){
        token = Jwts
            .builder()
            .expiration(getAccessTokenExpiry())
            .subject(user.getEmail())
            .claim("org-id", user.getOrganisationId())
            .claim("type", "refresh_token")
            .signWith(privateKey)
            .compact();
        return token;
    }


    public synchronized String generateToken(String audience, String scope) {
        Instant now = Instant.now();
        
        if(token != null && 
            expiresAt != null &&
            now.isBefore(expiresAt.minusSeconds(30))
        ){
            return token;
        }
        
        expiresAt = now.plusSeconds(360);
        token = Jwts
            .builder()
            .subject("auth-service")
            .audience()
            .add(audience)
            .and()
            .claim("scope", scope)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiresAt))
            .signWith(privateKey)
            .compact();
        
        return token;
    }
}