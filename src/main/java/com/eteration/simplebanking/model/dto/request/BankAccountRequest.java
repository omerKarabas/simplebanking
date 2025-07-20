package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BankAccountRequest(
    @NotBlank(message = "{validation.owner.required}")
    @Size(min = 2, max = 100, message = "{validation.owner.size}")
    String owner,
    
    @PositiveAmount
    double amount
) {} 