package com.secureapp.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 8 characters long and contain at least 1 uppercase letter, 1 lowercase letter, 1 digit, 1 symbol, and 1 letter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}