package com.eteration.simplebanking.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Transaction information")
public record TransactionResponse(
    @Schema(description = "Transaction date")
    LocalDateTime date,
    
    @Schema(description = "Transaction amount", example = "100.50")
    double amount,
    
    @Schema(description = "Transaction type", example = "DEPOSIT")
    String type,
    
    @Schema(description = "Approval code", example = "APP123456789")
    String approvalCode
) {
}