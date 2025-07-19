package com.eteration.simplebanking.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record BankAccountResponse(String accountNumber,
                                  String owner,
                                  double balance,
                                  LocalDateTime createDate,
                                  List<TransactionResponse> transactions
) {
}