package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.domain.validation.annotations.PositiveAmount;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
    @NotNull(message = "{validation.transaction.type.required}")
    TransactionType transactionType,
    
    @PositiveAmount
    double amount
) {} 