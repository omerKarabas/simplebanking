package com.eteration.simplebanking.service.core;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.interfaces.BankAccountService;
import com.eteration.simplebanking.service.interfaces.BankingFacadeService;
import com.eteration.simplebanking.service.interfaces.TransactionService;
import com.eteration.simplebanking.model.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public TransactionStatusResponse credit(String accountNumber, double amount) {
        log.info("Credit transaction: accountNumber={}, amount={}", accountNumber, amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        // Ensure account is saved before transaction
        account = bankAccountService.saveAccount(account);
        TransactionStatusResponse result = transactionService.credit(account, amount);
        return result;
    }

    @Override
    @Transactional
    public TransactionStatusResponse debit(String accountNumber, double amount) {
        log.info("Debit transaction: accountNumber={}, amount={}", accountNumber, amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        // Ensure account is saved before transaction
        account = bankAccountService.saveAccount(account);
        TransactionStatusResponse result = transactionService.debit(account, amount);
        return result;
    }

    @Override
    @Transactional
    public TransactionStatusResponse phoneBillPayment(String accountNumber, PhoneCompany phoneCompany, String phoneNumber, double amount) {
        log.info("Phone bill payment: accountNumber={}, phoneCompany={}, phoneNumber={}, amount={}", accountNumber, phoneCompany, phoneNumber, amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        // Ensure account is saved before transaction
        account = bankAccountService.saveAccount(account);
        TransactionStatusResponse result = transactionService.phoneBillPayment(account, phoneCompany, phoneNumber, amount);
        return result;
    }

    @Override
    @Transactional
    public TransactionStatusResponse checkPayment(String accountNumber, String payee, double amount) {
        log.info("Check payment: accountNumber={}, payee={}, amount={}", accountNumber, payee, amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        // Ensure account is saved before transaction
        account = bankAccountService.saveAccount(account);
        TransactionStatusResponse result = transactionService.checkPayment(account, payee, amount);
        return result;
    }
} 