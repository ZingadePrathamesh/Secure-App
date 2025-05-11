package com.secureapp.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {
    private String message;
    private String timeStamp;
    private String description;
    private Integer status;
}
