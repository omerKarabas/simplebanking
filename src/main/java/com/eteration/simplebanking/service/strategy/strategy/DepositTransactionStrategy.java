package com.eteration.simplebanking.service.strategy.strategy;

import com.eteration.simplebanking.domain.entity.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.service.strategy.TransactionStrategy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DepositTransactionStrategy implements TransactionStrategy {
    
    @Override
    public Transaction createTransaction(Object... parameters) {
        if (parameters.length < 1 || !(parameters[0] instanceof Double)) {
            throw new IllegalArgumentException("Amount parameter is required for deposit transaction");
        }
        
        double amount = (Double) parameters[0];
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        
        return transaction;
    }
    
    @Override
    public TransactionType getTransactionType() {
        return TransactionType.DEPOSIT;
    }
    
    @Override
    public String getOperationType() {
        return TransactionType.DEPOSIT.getOperationType();
    }
} 