package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;

public record TransactionRequest(
    @PositiveAmount
    double amount
) {} 