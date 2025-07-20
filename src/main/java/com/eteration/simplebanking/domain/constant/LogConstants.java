package com.eteration.simplebanking.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogConstants {
    
    DEFAULT_MASK("****"),
    PHONE_MASK("*****"),
    APPROVAL_MASK("****"),
    EMPTY_VALUE("-"),
    NAME_SUFFIX(".");
    
    private final String value;
} 