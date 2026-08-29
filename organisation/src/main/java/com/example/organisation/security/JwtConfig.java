package com.example.organisation.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

    @Value("${jwt.public-key-path}")
    private String publicKeyPath;

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {

        String publicKeyContent =
                Files.readString(Path.of(publicKeyPath));
                

        publicKeyContent = publicKeyContent
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes =
                Base64.getDecoder().decode(publicKeyContent);

        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        RSAPublicKey publicKey =
                (RSAPublicKey) keyFactory
                        .generatePublic(keySpec);

        NimbusJwtDecoder decoder =
                NimbusJwtDecoder.withPublicKey(publicKey)
                        .build();

        return decoder;
    }
}