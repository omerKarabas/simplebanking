package com.eteration.simplebanking.service.strategy;

import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.TransactionType;

/**
 * Strategy interface for different transaction types.
 * Each concrete strategy implements this interface to handle specific transaction types.
 */
public interface TransactionStrategy {
    
    /**
     * Creates a transaction with the given parameters.
     * 
     * @param parameters Variable parameters for transaction creation
     * @return Created transaction
     */
    Transaction createTransaction(Object... parameters);
    
    /**
     * Gets the transaction type this strategy handles.
     * 
     * @return TransactionType enum value
     */
    TransactionType getTransactionType();
    
    /**
     * Gets the operation type string for logging and response purposes.
     * 
     * @return Operation type string
     */
    String getOperationType();
} 