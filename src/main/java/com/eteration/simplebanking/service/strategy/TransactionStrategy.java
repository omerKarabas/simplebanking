package com.eteration.simplebanking.service.strategy;

import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.TransactionType;

public interface TransactionStrategy {
    
    Transaction createTransaction(Object... parameters);
    
    TransactionType getTransactionType();
    
    String getOperationType();
} 