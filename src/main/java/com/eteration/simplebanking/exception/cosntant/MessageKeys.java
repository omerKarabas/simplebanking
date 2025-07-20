package com.eteration.simplebanking.exception.cosntant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageKeys {

    ERROR_INSUFFICIENT_BALANCE("error.insufficient.balance"),
    ERROR_ACCOUNT_NOT_FOUND("error.account.not.found"),
    ERROR_INVALID_TRANSACTION("error.invalid.transaction"),
    ERROR_VALIDATION_FAILED("error.validation.failed"),
    ERROR_INVALID_REQUEST_BODY("error.invalid.request.body"),
    ERROR_INTERNAL_SERVER("error.internal.server"),

    ERROR_TITLE_INSUFFICIENT_BALANCE("error.title.insufficient.balance"),
    ERROR_TITLE_ACCOUNT_NOT_FOUND("error.title.account.not.found"),
    ERROR_TITLE_INVALID_TRANSACTION("error.title.invalid.transaction"),
    ERROR_TITLE_VALIDATION_ERROR("error.title.validation.error"),
    ERROR_TITLE_INVALID_REQUEST_BODY("error.title.invalid.request.body"),
    ERROR_TITLE_INTERNAL_SERVER_ERROR("error.title.internal.server.error"),

    VALIDATION_TRANSACTION_TYPE_NULL("validation.transaction.type.null"),
    VALIDATION_BANK_ACCOUNT_NULL("validation.bank.account.null"),
    VALIDATION_ACCOUNT_NUMBER_NULL_OR_EMPTY("validation.account.number.null.or.empty"),
    VALIDATION_TRANSACTION_PARAMETERS_NULL("validation.transaction.parameters.null"),
    VALIDATION_TRANSACTION_NULL("validation.transaction.null"),
    VALIDATION_TRANSACTION_AMOUNT_NEGATIVE("validation.transaction.amount.negative"),
    VALIDATION_TRANSACTION_DATE_NULL("validation.transaction.date.null"),
    
    VALIDATION_ACCOUNT_NUMBER_INVALID("validation.account.number.invalid"),
    VALIDATION_ACCOUNT_NUMBER_UNIQUE("validation.account.number.unique"),
    VALIDATION_PHONE_NUMBER_INVALID("validation.phone.number.invalid"),
    VALIDATION_AMOUNT_POSITIVE("validation.amount.positive"),
    VALIDATION_ACCOUNT_NUMBER_REQUIRED("validation.account.number.required"),
    VALIDATION_PHONE_NUMBER_REQUIRED("validation.phone.number.required"),
    VALIDATION_AMOUNT_REQUIRED("validation.amount.required"),

    INSUFFICIENT_BALANCE_FOR_PHONE_BILL("error.insufficient.balance.phone.bill"),
    INSUFFICIENT_BALANCE_FOR_CHECK_PAYMENT("error.insufficient.balance.check.payment"),
    ACCOUNT_NOT_FOUND_WITH_NUMBER("error.account.not.found.with.number"),
    
    STRATEGY_NOT_FOUND("error.strategy.not.found"),
    
    ERROR_ENCRYPTION_FAILED("error.encryption.failed"),
    ERROR_DECRYPTION_FAILED("error.decryption.failed");

    private final String key;
} 