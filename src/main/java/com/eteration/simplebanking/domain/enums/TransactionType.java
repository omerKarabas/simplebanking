package com.eteration.simplebanking.domain.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum TransactionType {
    DEPOSIT("Credit"),
    WITHDRAWAL("Debit"),
    PHONE_BILL_PAYMENT("PhoneBillPayment"),
    CHECK_PAYMENT("CheckPayment");
    
    private final String operationType;
} 