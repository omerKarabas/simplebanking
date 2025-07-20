package com.eteration.simplebanking.service.interfaces;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;

public interface TransactionService {
    
    TransactionStatusResponse credit(BankAccount account, double amount);
    
    TransactionStatusResponse debit(BankAccount account, double amount);
    
    TransactionStatusResponse phoneBillPayment(BankAccount account, PhoneCompany phoneCompany, String phoneNumber, double amount);
    
    TransactionStatusResponse checkPayment(BankAccount account, String payee, double amount);
} 