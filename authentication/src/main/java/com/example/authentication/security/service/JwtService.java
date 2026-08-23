package com.example.authentication.security.service;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
// import com.example.user.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private String key = "aGFwcGVuZWRtb29uYWN0aW9ud29tZW5xdWlldGV2ZW50dWFsbHlhbG1vc3RjYWdlZW4=";
    
    private SecretKey getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // public String generateRefreshToken(User user){
    //     return generateToken(user, 7 * 24 * 60 * 60 * 1000L);
    // }

    // public String generateAccessToken(User user){
    //     return generateToken(user, 15 * 60 * 1000);
    // }

    // private String generateToken(User user, long expiration){
    //     return Jwts
    //         .builder()
    //         .subject(user.getEmail())
    //         .claim("role", user.getRole().getName())
    //         .claim("userId", user.getId())
    //         // .claim("organisationId", user.getOrganisation().getId())
    //         .issuedAt(new Date())
    //         .expiration(
    //             new Date(
    //                 System.currentTimeMillis() + expiration
    //             )
    //         )
    //         .signWith(
    //             getSecretKey()
    //         )
    //         .compact();   
    // }

    public boolean isTokenValid(String token){
        return isTokenExpired(token); 
    }

    public Long getOrganisation(String token){
        return extractAllClaims(token).get("organisationId", Long.class);
    }

    public String getRole(String token){
        return extractAllClaims(token).get("role", String.class);
    }

    public Long getUserId(String token){
        return extractAllClaims(token).get("userId", Long.class);
    }

    private boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return extractAllClaims(token).getSubject();
    }
}
