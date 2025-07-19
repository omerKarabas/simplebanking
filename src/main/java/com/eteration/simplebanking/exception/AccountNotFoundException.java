package com.eteration.simplebanking.exception;

public class AccountNotFoundException extends RuntimeException {
    
    public AccountNotFoundException() {
        super();
    }
    
    public AccountNotFoundException(String message) {
        super(message);
    }
    
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 