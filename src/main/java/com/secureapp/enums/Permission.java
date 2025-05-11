package com.secureapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_GET("admin:get"),
    ADMIN_POST("admin:post"),
    ADMIN_PUT("admin:put"),
    ADMIN_DELETE("admin:delete"),

    STUDENT_GET("student:get"),
    STUDENT_POST("student:post"),
    STUDENT_PUT("student:put"),
    STUDENT_DELETE("student:delete"),

    PRINCIPAL_GET("principal:get"),
    PRINCIPAL_POST("principal:post"),
    PRINCIPAL_PUT("principal:put"),
    PRINCIPAL_DELETE("principal:delete"),

    TEACHER_GET("teacher:get"),
    TEACHER_POST("teacher:post"),
    TEACHER_PUT("teacher:put"),
    TEACHER_DELETE("teacher:delete");

    @Getter
    private final String permission;
}
