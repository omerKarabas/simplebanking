package com.eteration.simplebanking.model.dto.request;

public record CheckPaymentRequest(
    String payee,
    double amount
) {} 