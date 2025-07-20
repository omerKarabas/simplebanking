package com.eteration.simplebanking.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Transaction status response")
public record TransactionStatusResponse(
    @Schema(description = "Transaction status", example = "SUCCESS")
    String status,
    
    @Schema(description = "Approval code", example = "APP123456789")
    String approvalCode
) {
} 