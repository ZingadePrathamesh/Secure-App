package com.secureapp.mapper;

import com.secureapp.dto.RegistrationRequest;
import com.secureapp.enums.Role;
import com.secureapp.model.UserProfile;

import java.time.LocalDateTime;

public class UserMapper {



    public static UserProfile toUserProfile(RegistrationRequest re) {
        return UserProfile.builder()
                .id(null)
                .firstName(re.getFirstName())
                .lastName(re.getLastName())
                .email(re.getEmail())
                .password(re.getPassword())
                .enabled(false)
                .accountLocked(false)
                .role(Role.STUDENT)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
