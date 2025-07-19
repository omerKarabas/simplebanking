package com.eteration.simplebanking.service.impl;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.BankAccountService;
import com.eteration.simplebanking.service.BankingFacadeService;
import com.eteration.simplebanking.service.TransactionService;
import com.eteration.simplebanking.model.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankingFacadeServiceImpl implements BankingFacadeService {

    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    @Override
    public BankAccountResponse createBankAccount(String owner, String accountNumber) {
        log.info("Creating bank account: owner={}, accountNumber={}", owner, accountNumber);
        return bankAccountService.createAccount(owner, accountNumber);
    }

    @Override
    public BankAccountResponse getBankAccount(String accountNumber) {
        log.info("Getting bank account information: accountNumber={}", accountNumber);
        return bankAccountService.getAccount(accountNumber);
    }

    @Override
    public TransactionStatusResponse credit(String accountNumber, double amount) {
        log.info("Credit transaction: accountNumber={}, amount={}", accountNumber, amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        TransactionStatusResponse result = transactionService.credit(account, amount);
        bankAccountService.saveAccount(account);
        return result;
    }

    @Override
    public TransactionStatusResponse debit(String accountNumber, double amount) {
        log.info("Debit transaction: accountNumber={}, amount={}", accountNumber, amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        TransactionStatusResponse result = transactionService.debit(account, amount);
        bankAccountService.saveAccount(account);
        return result;
    }
} 