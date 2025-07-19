package com.eteration.simplebanking.service;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;

public interface BankingFacadeService {
    
    BankAccountResponse createBankAccount(String owner, String accountNumber);
    
    BankAccountResponse getBankAccount(String accountNumber);
    
    TransactionStatusResponse credit(String accountNumber, double amount) throws InsufficientBalanceException;
    
    TransactionStatusResponse debit(String accountNumber, double amount) throws InsufficientBalanceException;
} 