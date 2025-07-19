package com.eteration.simplebanking.model.mapper;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {

    public BankAccountResponse toAccountResponse(BankAccount account) {
        List<TransactionResponse> transactionResponses = account.getTransactions().stream()
                .map(this::toTransactionResponse)
                .toList();

        return new BankAccountResponse(
                account.getAccountNumber(),
                account.getOwner(),
                account.getBalance(),
                account.getCreatedAt(),
                transactionResponses
        );
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getClass().getSimpleName(),
                transaction.getApprovalCode()
        );
    }
} 