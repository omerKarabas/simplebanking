package com.eteration.simplebanking.exception;

import com.eteration.simplebanking.exception.cosntant.MessageKeys;

public class InsufficientBalanceException extends Exception {
    
    private final MessageKeys messageKey;
    
    public InsufficientBalanceException() {
        super();
        this.messageKey = null;
    }
    
    public InsufficientBalanceException(String message) {
        super(message);
        this.messageKey = null;
    }
    
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
        this.messageKey = null;
    }
    
    public InsufficientBalanceException(MessageKeys messageKey) {
        super(messageKey.getKey());
        this.messageKey = messageKey;
    }
    
    public InsufficientBalanceException(MessageKeys messageKey, Throwable cause) {
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
