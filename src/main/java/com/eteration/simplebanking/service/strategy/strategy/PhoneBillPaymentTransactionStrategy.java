package com.eteration.simplebanking.service.strategy.strategy;

import com.eteration.simplebanking.domain.entity.transaction.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.service.strategy.TransactionStrategy;
import org.springframework.stereotype.Component;

@Component
public class PhoneBillPaymentTransactionStrategy implements TransactionStrategy {
    
    @Override
    public Transaction createTransaction(Object... parameters) {
        if (parameters.length < 3) {
            throw new IllegalArgumentException(com.eteration.simplebanking.exception.cosntant.MessageKeys.ERROR_INVALID_TRANSACTION.getKey());
        }
        
        if (!(parameters[0] instanceof com.eteration.simplebanking.domain.enums.PhoneCompany phoneCompany)) {
            throw new IllegalArgumentException(com.eteration.simplebanking.exception.cosntant.MessageKeys.ERROR_INVALID_TRANSACTION.getKey());
        }
        
        if (!(parameters[1] instanceof String phoneNumber)) {
            throw new IllegalArgumentException(com.eteration.simplebanking.exception.cosntant.MessageKeys.ERROR_INVALID_TRANSACTION.getKey());
        }
        
        if (!(parameters[2] instanceof Double)) {
            throw new IllegalArgumentException(com.eteration.simplebanking.exception.cosntant.MessageKeys.ERROR_INVALID_TRANSACTION.getKey());
        }

        double amount = (Double) parameters[2];
        
        return new PhoneBillPaymentTransaction(phoneCompany, phoneNumber, amount);
    }
    
    @Override
    public TransactionType getTransactionType() {
        return TransactionType.PHONE_BILL_PAYMENT;
    }
    
    @Override
    public String getOperationType() {
        return TransactionType.PHONE_BILL_PAYMENT.getOperationType();
    }
} 