package com.eteration.simplebanking.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Schema(description = "Transaction types")
public enum TransactionType {
    @Schema(description = "Deposit transaction")
    DEPOSIT("Credit"),
    
    @Schema(description = "Withdrawal transaction")
    WITHDRAWAL("Debit"),
    
    @Schema(description = "Phone bill payment transaction")
    PHONE_BILL_PAYMENT("PhoneBillPayment"),
    
    @Schema(description = "Check payment transaction")
    CHECK_PAYMENT("CheckPayment");
    
    private final String operationType;
} 