package com.secureapp.service;

import com.secureapp.exceptions.AccountLockedException;
import com.secureapp.model.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final BruteForceProtectionService bruteForceProtectionService;
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authenticate method");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if(bruteForceProtectionService.isBlocked(username)){
            System.out.println("Login failed : " + "account blocked");
            throw new AccountLockedException("Account is locked due to too many login fails! Kindly, wait for some time before trying.");
        }

        UserProfile userProfile = (UserProfile) userDetailsService.loadUserByUsername(username);

        if(userProfile == null || !passwordEncoder.matches(password, userProfile.getPassword())){
            System.out.println("password matching failed");
            bruteForceProtectionService.loginFailed(username);
            throw new BadCredentialsException("Invalid username or password");
        }

        System.out.println("Password matched!");
        bruteForceProtectionService.loginSucceed(username);
        return new UsernamePasswordAuthenticationToken(username, password, userProfile.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
