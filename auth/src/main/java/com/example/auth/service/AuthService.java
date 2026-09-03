package com.example.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.role.RoleResponse;
import com.example.auth.dto.token.RefreshTokenRequest;
import com.example.auth.dto.token.RefreshTokenResponse;
import com.example.auth.dto.token.TokenResponse;
import com.example.auth.exception.AccountLockedException;
import com.example.auth.exception.UnauthorizedException;
import com.example.auth.mapper.AuthUserMapper;
import com.example.auth.models.AuthUser;
import com.example.auth.models.Organisation;
import com.example.auth.models.Role;
import com.example.auth.repository.AuthUserRepo;
import com.example.auth.repository.OrganisationRepository;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    private final AuthUserRepo authUserRepo;
    private final OrganisationRepository organisationRepository;
    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleService roleService;

    private static final int MAX_LIMIT = 5;
    private static final int RESET_LIMIT = 0;
    private static final int LOCK_MAX_MINUTE = 15;

    public AuthService(
        AuthUserRepo authUserRepo,
        OrganisationRepository organisationRepository,
        AuthUserMapper authUserMapper,
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        RoleService roleService
    ) {
        this.authUserRepo = authUserRepo;
        this.organisationRepository = organisationRepository;
        this.authUserMapper = authUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleService = roleService;
    }

    @Transactional
    public void registerUser(RegisterRequest request) {
        Organisation organisation = new Organisation();
        organisation.setName(request.organisation());
        organisation = organisationRepository.save(organisation);

        Role role = new Role();
        role.setName("ORG_ADMIN");
        role.setOrganisation(organisation);
        Role roleResponse = roleService.createRole(role);

        AuthUser authUser = authUserMapper.toAuthUser(request);
        authUser.setOrganisation(organisation);
        authUser.setPassword(passwordEncoder.encode(request.password()));
        authUser.setEnabled(true);
        authUser.getRoles().add(roleResponse);
        authUserRepo.save(authUser);
    }

    private void refreshLoginAttempts(AuthUser user) {
        user.setLockUntilAt(null);
        user.setLoginAttempts(RESET_LIMIT);
        authUserRepo.save(user);
    }

    public TokenResponse loginUser(LoginRequest request) {
        AuthUser user = authUserRepo.findByEmail(request.email())
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (user.getLoginAttempts() >= MAX_LIMIT &&
            user.getLastLoginAt() != null &&
            user.getLockUntilAt().isAfter(Instant.now())
        ) {
            throw new AccountLockedException(
                "Account has been locked. Try after " + LOCK_MAX_MINUTE + " minutes"
            );
        }

        boolean isValid = passwordEncoder.matches(request.password(), user.getPassword());

        if (!isValid) {
            user.setLoginAttempts(user.getLoginAttempts() + 1);

            if (user.getLoginAttempts() >= MAX_LIMIT) {
                user.setLockUntilAt(Instant.now().plus(LOCK_MAX_MINUTE, ChronoUnit.MINUTES));
                authUserRepo.save(user);
                throw new AccountLockedException(
                    "Account has been locked. Try after " + LOCK_MAX_MINUTE + " minutes"
                );
            }

            authUserRepo.save(user);
            throw new BadCredentialsException("Invalid Credentials");
        }

        refreshLoginAttempts(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new TokenResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        if(!jwtService.isRefreshTokenValid(request.refreshToken())){
           throw new UnauthorizedException("user is not authenticated"); 
        }
        String email = jwtService.getSubject(request.refreshToken());
        AuthUser user = authUserRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        String accessToken = jwtService.generateAccessToken(user);
    
        RefreshTokenResponse response = new RefreshTokenResponse();
        response.setAccessToken(accessToken);
        return response;
    }

    
}
