package com.eteration.simplebanking.service.interfaces;

import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;

public interface BankingFacadeService {
    
    BankAccountResponse createBankAccount(String owner, String accountNumber);
    
    BankAccountResponse getBankAccount(String accountNumber);
    
    TransactionStatusResponse credit(String accountNumber, double amount);
    
    TransactionStatusResponse debit(String accountNumber, double amount);
    
    TransactionStatusResponse phoneBillPayment(String accountNumber, PhoneCompany phoneCompany, String phoneNumber, double amount);
    
    TransactionStatusResponse checkPayment(String accountNumber, String payee, double amount);
} 