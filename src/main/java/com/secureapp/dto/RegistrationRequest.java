package com.secureapp.dto;

import com.secureapp.annotations.ValidPassword;
import com.secureapp.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {

    @NotBlank(message = "lastName cannot be blank!")
    @Size(message = "firstName should have 1 to 20 characters", min = 1, max = 20)
    private String firstName;

    @NotBlank(message = "lastName cannot be blank!")
    @Size(message = "lastName should have 2 to 20 characters", min = 2, max = 20)
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "email cannot be blank!")
    @Email(message = "Invalid email address!")
    private String email;

    @NotBlank(message = "password cannot be blank!")
    @Size(message = "password should have 8 to 20 characters", min = 8, max = 20)
    @ValidPassword
    private String password;
}
