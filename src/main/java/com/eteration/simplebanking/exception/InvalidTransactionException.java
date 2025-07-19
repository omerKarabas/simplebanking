package com.eteration.simplebanking.exception;

public class InvalidTransactionException extends RuntimeException {
    
    public InvalidTransactionException() {
        super();
    }
    
    public InvalidTransactionException(String message) {
        super(message);
    }
    
    public InvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
} 