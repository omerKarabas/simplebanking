package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhoneBillPaymentRequest(
    @NotBlank(message = "{validation.phone.number.required}")
    String phoneNumber,
    
    @NotNull(message = "{validation.phone.company.required}")
    PhoneCompany phoneCompany,
    
    @PositiveAmount
    double amount
) {} 