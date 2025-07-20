package com.eteration.simplebanking.model.dto.request;

import com.eteration.simplebanking.domain.enums.PhoneCompany;

public record PhoneBillPaymentRequest(
    PhoneCompany phoneCompany,
    String phoneNumber,
    double amount
) {} 