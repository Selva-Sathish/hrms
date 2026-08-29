package com.example.auth.service;

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

    @Bean
    JwtDecoder jwtDecoder(
            @Value("${jwt.public-key-path}")
            String publicKeyPath) throws Exception {

        String key = Files.readString(
                Path.of(publicKeyPath)
        )
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replaceAll("\\s", "");

        byte[] keyBytes =
                Base64.getDecoder().decode(key);

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        RSAPublicKey publicKey =
                (RSAPublicKey) keyFactory
                        .generatePublic(spec);

        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }
}
