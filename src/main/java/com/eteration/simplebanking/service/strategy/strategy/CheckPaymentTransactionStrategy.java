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
            throw new IllegalArgumentException(com.eteration.simplebanking.exception.cosntant.MessageKeys.ERROR_INVALID_TRANSACTION.getKey());
        }
        
        if (!(parameters[0] instanceof String payee)) {
            throw new IllegalArgumentException(com.eteration.simplebanking.exception.cosntant.MessageKeys.ERROR_INVALID_TRANSACTION.getKey());
        }
        
        if (!(parameters[1] instanceof Double)) {
            throw new IllegalArgumentException(com.eteration.simplebanking.exception.cosntant.MessageKeys.ERROR_INVALID_TRANSACTION.getKey());
        }

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