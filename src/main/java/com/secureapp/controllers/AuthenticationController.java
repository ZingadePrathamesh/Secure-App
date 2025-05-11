package com.secureapp.controllers;

import com.secureapp.dto.AuthenticationRequest;
import com.secureapp.dto.AuthenticationResponse;
import com.secureapp.dto.RegistrationRequest;
import com.secureapp.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("sign-up")
    public ResponseEntity signUp(
            @RequestBody @Valid RegistrationRequest registrationRequest
    ) throws MessagingException {
        authenticationService.signUp(registrationRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/token")
    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < 6; i++){
            stringBuilder.append(secureRandom.nextInt(9));
        }
        return stringBuilder.toString();
    }

    @PostMapping("sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(
            @RequestBody AuthenticationRequest authRequest
    ) throws AuthenticationException {
        return ResponseEntity.ok(authenticationService.signIn(authRequest));
    }

    @GetMapping("activate-account")
    public ResponseEntity activateAccount(
            @RequestParam String token
    ) throws AuthenticationException, MessagingException {
        authenticationService.activateAccount(token);
        return ResponseEntity.accepted().build();
    }
}
