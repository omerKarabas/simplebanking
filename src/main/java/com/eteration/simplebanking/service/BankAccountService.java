package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;

public interface BankAccountService {

    BankAccountResponse createAccount(String owner, String accountNumber);
    
    BankAccountResponse getAccount(String accountNumber);
    
    void saveAccount(BankAccount account);

    BankAccount findAccountByNumber(String accountNumber);
}
