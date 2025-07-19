package com.eteration.simplebanking.exception.cosntant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageKeys {

    // Error Messages
    ERROR_INSUFFICIENT_BALANCE("error.insufficient.balance"),
    ERROR_ACCOUNT_NOT_FOUND("error.account.not.found"),
    ERROR_INVALID_TRANSACTION("error.invalid.transaction"),
    ERROR_VALIDATION_FAILED("error.validation.failed"),
    ERROR_INVALID_REQUEST_BODY("error.invalid.request.body"),
    ERROR_INTERNAL_SERVER("error.internal.server"),

    // Error Titles
    ERROR_TITLE_INSUFFICIENT_BALANCE("error.title.insufficient.balance"),
    ERROR_TITLE_ACCOUNT_NOT_FOUND("error.title.account.not.found"),
    ERROR_TITLE_INVALID_TRANSACTION("error.title.invalid.transaction"),
    ERROR_TITLE_VALIDATION_ERROR("error.title.validation.error"),
    ERROR_TITLE_INVALID_REQUEST_BODY("error.title.invalid.request.body"),
    ERROR_TITLE_INTERNAL_SERVER_ERROR("error.title.internal.server.error");

    private final String key;
} 