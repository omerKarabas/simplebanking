package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request for transaction operations")
public record TransactionRequest(
    @Schema(description = "Transaction amount", example = "100.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @PositiveAmount
    double amount
) {} 