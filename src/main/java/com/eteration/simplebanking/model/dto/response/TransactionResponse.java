package com.eteration.simplebanking.model.dto.response;

import java.time.LocalDateTime;

public record TransactionResponse(LocalDateTime date,
                                  double amount,
                                  String type,
                                  String approvalCode
) {
}