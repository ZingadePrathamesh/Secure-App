package com.secureapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmailTemplate {
    ACTIVATION_ACCOUNT("activation_account");

    @Getter
    private final String name;
}
