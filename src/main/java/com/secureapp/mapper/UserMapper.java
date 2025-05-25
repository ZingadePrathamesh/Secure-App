package com.secureapp.mapper;

import com.secureapp.dto.RegistrationRequest;
import com.secureapp.dto.UserDTO;
import com.secureapp.enums.Role;
import com.secureapp.model.UserProfile;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class UserMapper {

    public static Page<UserDTO> toUserDTOs(Page<UserProfile> userProfiles) {
        return userProfiles.map(user -> UserDTO.builder()
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build());
    }

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
