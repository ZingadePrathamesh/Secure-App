package com.secureapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException(String s) {
        super(s);
    }
}
