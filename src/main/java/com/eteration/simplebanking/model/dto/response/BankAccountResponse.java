package com.eteration.simplebanking.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Bank account information")
public record BankAccountResponse(
    @Schema(description = "Account number", example = "1234567890")
    String accountNumber,
    
    @Schema(description = "Account owner name", example = "John Doe")
    String owner,
    
    @Schema(description = "Current balance", example = "1000.50")
    double balance,
    
    @Schema(description = "Account creation date")
    LocalDateTime createdAt,
    
    @Schema(description = "List of transactions")
    List<TransactionResponse> transactions
) {
}