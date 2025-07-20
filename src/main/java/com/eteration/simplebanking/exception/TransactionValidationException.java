package com.eteration.simplebanking.exception;

import com.eteration.simplebanking.exception.cosntant.MessageKeys;

/**
 * Exception thrown when transaction validation fails.
 */
public class TransactionValidationException extends RuntimeException {
    
    private final MessageKeys messageKey;
    
    public TransactionValidationException(String message) {
        super(message);
        this.messageKey = null;
    }
    
    public TransactionValidationException(String message, Throwable cause) {
        super(message, cause);
        this.messageKey = null;
    }
    
    public TransactionValidationException(MessageKeys messageKey) {
        super(messageKey.getKey());
        this.messageKey = messageKey;
    }
    
    public TransactionValidationException(MessageKeys messageKey, Throwable cause) {
        super(messageKey.getKey(), cause);
        this.messageKey = messageKey;
    }
    
    public MessageKeys getMessageKey() {
        return messageKey;
    }
    
    public boolean hasMessageKey() {
        return messageKey != null;
    }
} 