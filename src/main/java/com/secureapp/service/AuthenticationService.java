package com.secureapp.service;

import com.secureapp.dto.AuthenticationRequest;
import com.secureapp.dto.AuthenticationResponse;
import com.secureapp.dto.RegistrationRequest;
import com.secureapp.enums.EmailTemplate;
import com.secureapp.enums.Role;
import com.secureapp.exceptions.TokenNotFoundException;
import com.secureapp.exceptions.UserNotEnabledException;
import com.secureapp.model.Token;
import com.secureapp.model.UserProfile;
import com.secureapp.respository.TokenRepository;
import com.secureapp.respository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static com.secureapp.mapper.UserMapper.toUserProfile;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JWTService jwtService;
    private final CustomAuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    private String confirmationUrl = "http://localhost:8080/api/v1/auth/activate-account";

    private Token getByToken(String token){
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found!"));
    }


    @Bean
    public CommandLineRunner commandLineRunner(
            UserService userService,
            PasswordEncoder passwordEncoder
    ){
        return args -> {

        };
    }

    public void signUp(RegistrationRequest request) throws MessagingException {
        UserProfile userProfile = toUserProfile(request);
        userProfile.setPassword(passwordEncoder.encode(request.getPassword()));

        UserProfile userProfile1 = userService.saveStudent(userProfile);
        sendEmailValidation(userProfile1);
    }

    private void sendEmailValidation(UserProfile userProfile) throws MessagingException {
        String token = generateAndSaveValidationToken(userProfile);
        emailService.sendEmail(userProfile.getEmail(),
                userProfile.getFullName(),
                EmailTemplate.ACTIVATION_ACCOUNT,
                confirmationUrl + "?token=" + token,
                token,
                "Activation account");
    }

    private String generateAndSaveValidationToken(UserProfile userProfile) {
        String tokenString = generateToken(6);
        Token token = Token.builder()
                .id(null)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .userProfile(userProfile)
                .token(tokenString)
                .build();
        tokenRepository.save(token);
        return tokenString;
    }

    private String generateToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++){
            stringBuilder.append(secureRandom.nextInt(9));
        }
        return stringBuilder.toString();
    }

    public void activateAccount(String token) throws MessagingException {
        Token token1 = getByToken(token);
        UserProfile userProfile = token1.getUserProfile();
        if(LocalDateTime.now().isAfter(token1.getExpiresAt())){
            sendEmailValidation(userProfile);
            throw new TokenNotFoundException("Token is expired.");
        }else{
            userProfile.setEnabled(true);
            token1.setValidatedAt(LocalDateTime.now());
            userRepository.save(userProfile);
            tokenRepository.save(token1);
        }
    }

    public AuthenticationResponse signIn(AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(), authRequest.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = userService.getByUsername(authRequest.getEmail());
                String token = jwtService.generateToken(userDetails);

                return AuthenticationResponse.builder()
                        .token(token)
                        .timeStamp(LocalDateTime.now())
                        .build();
            }

            throw new RuntimeException("Authentication failed");

        } catch (DisabledException ex) {
            throw new UserNotEnabledException("Your account is not yet enabled. Please verify your email or contact support.");
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid email or password.");
        }
    }
}
