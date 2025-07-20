package com.eteration.simplebanking.exception;

import com.eteration.simplebanking.exception.cosntant.MessageKeys;

public class StrategyNotFoundException extends RuntimeException {
    
    private final MessageKeys messageKey;
    private final Object[] parameters;
    
    public StrategyNotFoundException() {
        super();
        this.messageKey = null;
        this.parameters = null;
    }
    
    public StrategyNotFoundException(String message) {
        super(message);
        this.messageKey = null;
        this.parameters = null;
    }
    
    public StrategyNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.messageKey = null;
        this.parameters = null;
    }
    
    public StrategyNotFoundException(MessageKeys messageKey) {
        super(messageKey.getKey());
        this.messageKey = messageKey;
        this.parameters = null;
    }
    
    public StrategyNotFoundException(MessageKeys messageKey, Throwable cause) {
        super(messageKey.getKey(), cause);
        this.messageKey = messageKey;
        this.parameters = null;
    }
    
    public StrategyNotFoundException(MessageKeys messageKey, Object... parameters) {
        super(messageKey.getKey());
        this.messageKey = messageKey;
        this.parameters = parameters;
    }
    
    public MessageKeys getMessageKey() {
        return messageKey;
    }
    
    public boolean hasMessageKey() {
        return messageKey != null;
    }
    
    public Object[] getParameters() {
        return parameters;
    }
} 