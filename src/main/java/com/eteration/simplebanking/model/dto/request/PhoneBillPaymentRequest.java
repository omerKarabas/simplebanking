package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request for phone bill payment")
public record PhoneBillPaymentRequest(
    @Schema(description = "Phone number", example = "5551234567", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.phone.number.required}")
    String phoneNumber,
    
    @Schema(description = "Phone company", example = "TURKCELL", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "{validation.phone.company.required}")
    PhoneCompany phoneCompany,
    
    @Schema(description = "Payment amount", example = "50.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @PositiveAmount
    double amount
) {} 