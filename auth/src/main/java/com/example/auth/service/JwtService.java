package com.example.auth.service;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService{

    @Value("${jwt.secret}")
    private String privateKey;
    
    public SecretKey getSecretKey(){
        byte[] secretKey = Base64.getDecoder().decode(privateKey);
        return Keys.hmacShaKeyFor(secretKey);
    }  

    private Date getAccessTokenExpiry(){
        return Date.from(Instant.now().plus(15, ChronoUnit.MINUTES));
    }

    public Date getRefreshTokenExpiry(){
        return Date.from(Instant.now().plus(90, ChronoUnit.DAYS));
    }
    
    public String generateRefreshToken(){
        return Jwts
            .builder()
            .claim("type", "refresh_token")
            .expiration(getRefreshTokenExpiry())
            .signWith(getSecretKey())
            .compact();
    } 

    public String generateAccessToken(){
        return Jwts
            .builder()
            .expiration(getAccessTokenExpiry())
            .claim("type", "refresh_token")
            .signWith(getSecretKey())
            .compact();
    }

    public Claims parsePayload(String token){
        return Jwts
            .parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

}