package com.secureapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private boolean enable = true;
    private int page;
    private int size = 10;
}
