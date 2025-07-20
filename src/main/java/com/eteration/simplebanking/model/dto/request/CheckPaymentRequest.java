package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for check payment")
public record CheckPaymentRequest(
    @Schema(description = "Payee name", example = "ABC Company", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.payee.required}")
    @Size(min = 2, max = 100, message = "{validation.payee.size}")
    String payee,
    
    @Schema(description = "Payment amount", example = "250.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @PositiveAmount
    double amount
) {} 