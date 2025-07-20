package com.eteration.simplebanking.service.strategy.strategy;

import com.eteration.simplebanking.domain.entity.transaction.CheckTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.service.strategy.TransactionStrategy;
import org.springframework.stereotype.Component;

@Component
public class CheckPaymentTransactionStrategy implements TransactionStrategy {
    
    @Override
    public Transaction createTransaction(Object... parameters) {
        if (parameters.length < 2) {
            throw new IllegalArgumentException("Payee and amount parameters are required for check payment transaction");
        }
        
        if (!(parameters[0] instanceof String)) {
            throw new IllegalArgumentException("First parameter must be String (payee)");
        }
        
        if (!(parameters[1] instanceof Double)) {
            throw new IllegalArgumentException("Second parameter must be Double (amount)");
        }
        
        String payee = (String) parameters[0];
        double amount = (Double) parameters[1];
        
        return new CheckTransaction(payee, amount);
    }
    
    @Override
    public TransactionType getTransactionType() {
        return TransactionType.CHECK_PAYMENT;
    }
    
    @Override
    public String getOperationType() {
        return TransactionType.CHECK_PAYMENT.getOperationType();
    }
} 