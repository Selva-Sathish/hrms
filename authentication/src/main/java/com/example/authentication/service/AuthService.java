// package com.example.user.service;

// import java.time.Duration;
// import java.time.Instant;

// import org.springframework.http.ResponseCookie;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import com.example.user.dto.user.CreateUserRequest;
// import com.example.user.dto.auth.LoginRequest;
// import com.example.user.dto.auth.TokenResponse;
// import com.example.user.dto.event.UserCreateEvent;
// import com.example.user.exception.BadRequestException;
// import com.example.user.kafka.producer.UserProducer;
// import com.example.user.mapper.UserMapper;
// // import com.example.user.models.Organisation;
// import com.example.user.models.Role;
// import com.example.user.models.User;
// import com.example.user.repository.UserRepository;
// import com.example.user.security.service.JwtService;
// import jakarta.transaction.Transactional;

// @Service
// public class AuthService {
//     private final UserService userService;
//     private final PasswordEncoder passwordEncoder;
//     // private final OrganisationService organisationService;
//     private final UserMapper userMappper;
//     private final UserRepository userRepository;
//     private final UserProducer userProducer;
//     private final RoleService roleService;
//     private final JwtService jwtService;
//     private UserValidationService userValidationService;
    
//     private final int MAX_LIMIT = 5;
//     private final int RESET_LIMIT = 0;
//     public AuthService(
//         UserService userService,
//         UserRepository userRepository,
//         // OrganisationService organisationService,
//         PasswordEncoder passwordEncoder,
//         UserMapper userMappper,
//         UserProducer userProducer,
//         JwtService jwtService,
//         RoleService roleService,
//         UserValidationService userValidationService
        
//     ){
//         this.userService = userService;
//         // this.organisationService = organisationService;
//         this.passwordEncoder = passwordEncoder;
//         this.userMappper = userMappper;
//         this.userRepository = userRepository;
//         this.userProducer = userProducer;
//         this.jwtService = jwtService;
//         this.roleService = roleService;
//         this.userValidationService = userValidationService;
//     }

//     public void validatePassword(String password, String confirmPassword){
//         if(!password.equals(confirmPassword)){
//             throw new BadRequestException("password mismatch");
//         }
//     }

//     @Transactional
//     public void registerUser(CreateUserRequest request) {
//         // Organisation createdOrg = organisationService.createOrganisation(request.organisation());
//         userValidationService.validateEmailNotExists(request.email());
//         validatePassword(request.password(), request.confirmPassword());   
//         String hashPassword = passwordEncoder.encode(request.password()); 
//         Role role = roleService.createRole("ROLE_ADMIN", createdOrg);
//         User user = userMappper.toEntity(request);
//         // user.setOrganisation(createdOrg);
//         user.setActive(true);
//         user.setPassword(hashPassword);
//         user.setRole(role);
//         user = userRepository.save(user);
//         UserCreateEvent event = userMappper.toUserCreateEvent(user);
//         userProducer.publishUserCreated(event);
//     }

//     public boolean isAccountLocked(User user){
//         return user.isAccountLocked()
//             && user.getLockedAt() != null
//             && user.getLockedAt()
//                 .plus(Duration.ofMinutes(15))
//                 .isAfter(Instant.now());
//     }

//     public void refreshLoginAttempts(User user){
//         user.setAccountLocked(false);
//         user.setFailedLoginAttempts(RESET_LIMIT);
//         user.setLockedAt(null);
//     }

//     @Transactional
//     public TokenResponse login(LoginRequest request) {
//         User user = userService.getByEmail(request.email());
        
//         if (user.isAccountLocked() && !isAccountLocked(user)) {
//             refreshLoginAttempts(user);
//             userRepository.save(user);
//         }

//         if(isAccountLocked(user)){
//             throw new BadRequestException("Accouny has been locked until " + user.getLockedAt());
//         }
//         if(!passwordEncoder.matches(request.password(), user.getPassword())){
//             user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1 );
//             if(user.getFailedLoginAttempts() >= MAX_LIMIT){
//                 user.setAccountLocked(true);
//                 user.setLockedAt(Instant.now());
//             }            
//             userRepository.save(user);   
//             throw new BadRequestException("username or password invalid");
//         }
//         String accessToken = jwtService.generateAccessToken(user);
//         String refreshToken = jwtService.generateRefreshToken(user);        
//         refreshLoginAttempts(user);

//         return new TokenResponse(accessToken, refreshToken);
//     }

//     public ResponseCookie createRefreshToken(String refreshToken) {
        
//         if(jwtService.isTokenValid(refreshToken)){
//             throw new BadRequestException("Invalid refresh token");
//         }

//         String email = jwtService.getSubject(refreshToken);
        
//         User user = userService.getByEmail(email);
        
//         String accessToken = jwtService.generateRefreshToken(user);
//         ResponseCookie at = ResponseCookie.from("access_token", accessToken)
//             .httpOnly(true)
//             .secure(false)
//             .path("/")
//             .sameSite("Strict")
//             .maxAge(Duration.ofMinutes(15))
//             .build();
//         return at;
//     }

// }
