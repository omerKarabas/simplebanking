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
import com.eteration.simplebanking.util.SecureMaskUtil;
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
    private final SecureMaskUtil secureMaskUtil;

    @Override
    public BankAccountResponse createBankAccount(String owner, String accountNumber) {
        log.debug("[CREATE_ACCOUNT] Owner: {}, Account: {}", secureMaskUtil.encryptName(owner), secureMaskUtil.encryptAccount(accountNumber));
        return bankAccountService.createAccount(owner, accountNumber);
    }

    @Override
    public BankAccountResponse getBankAccount(String accountNumber) {
        log.debug("[GET_ACCOUNT] Account: {}", secureMaskUtil.encryptAccount(accountNumber));
        return bankAccountService.getAccount(accountNumber);
    }

    @Override
    @Transactional
    public TransactionStatusResponse credit(String accountNumber, double amount) {
        log.debug("[CREDIT] Account: {}, Amount: {}", secureMaskUtil.encryptAccount(accountNumber), amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        account = bankAccountService.saveAccount(account);
        return transactionService.credit(account, amount);
    }

    @Override
    @Transactional
    public TransactionStatusResponse debit(String accountNumber, double amount) {
        log.debug("[DEBIT] Account: {}, Amount: {}", secureMaskUtil.encryptAccount(accountNumber), amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        account = bankAccountService.saveAccount(account);
        return transactionService.debit(account, amount);
    }

    @Override
    @Transactional
    public TransactionStatusResponse phoneBillPayment(String accountNumber, PhoneCompany phoneCompany, String phoneNumber, double amount) {
        log.debug("[PHONE_BILL] Account: {}, PhoneCompany: {}, Phone: {}, Amount: {}", 
                secureMaskUtil.encryptAccount(accountNumber), phoneCompany, secureMaskUtil.encryptPhone(phoneNumber), amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        account = bankAccountService.saveAccount(account);
        return transactionService.phoneBillPayment(account, phoneCompany, phoneNumber, amount);
    }

    @Override
    @Transactional
    public TransactionStatusResponse checkPayment(String accountNumber, String payee, double amount) {
        log.debug("[CHECK_PAYMENT] Account: {}, Payee: {}, Amount: {}", 
                secureMaskUtil.encryptAccount(accountNumber), secureMaskUtil.encryptPayee(payee), amount);
        BankAccount account = bankAccountService.findAccountByNumber(accountNumber);
        account = bankAccountService.saveAccount(account);
        return transactionService.checkPayment(account, payee, amount);
    }
} 