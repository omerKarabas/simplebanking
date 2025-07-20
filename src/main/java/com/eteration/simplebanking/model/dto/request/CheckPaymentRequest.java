package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckPaymentRequest(
    @NotBlank(message = "{validation.payee.required}")
    @Size(min = 2, max = 100, message = "{validation.payee.size}")
    String payee,
    
    @PositiveAmount
    double amount
) {} 