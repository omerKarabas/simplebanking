package com.eteration.simplebanking.service.strategy.strategy;

import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.entity.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.service.strategy.TransactionStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class WithdrawalTransactionStrategy implements TransactionStrategy {
    
    @Override
    public Transaction createTransaction(Object... parameters) {
        if (parameters.length < 1 || !(parameters[0] instanceof Double)) {
            throw new IllegalArgumentException("Amount parameter is required for withdrawal transaction");
        }
        
        double amount = (Double) parameters[0];
        WithdrawalTransaction transaction = new WithdrawalTransaction();
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        
        return transaction;
    }
    
    @Override
    public TransactionType getTransactionType() {
        return TransactionType.WITHDRAWAL;
    }
    
    @Override
    public String getOperationType() {
        return TransactionType.WITHDRAWAL.getOperationType();
    }
} 