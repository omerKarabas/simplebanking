package com.eteration.simplebanking.service.core;

import com.eteration.simplebanking.constant.CacheConstants;
import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.interfaces.TransactionService;
import com.eteration.simplebanking.service.strategy.TransactionStrategyFactory;
import com.eteration.simplebanking.util.MaskUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionStrategyFactory strategyFactory;

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "#account.accountNumber")
    public TransactionStatusResponse credit(BankAccount account, double amount) {
        log.debug("[CREDIT] Account: {}, Amount: {}", MaskUtil.maskAccount(account.getAccountNumber()), amount);
        return strategyFactory.executeTransaction(TransactionType.DEPOSIT, account, amount);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "#account.accountNumber")
    public TransactionStatusResponse debit(BankAccount account, double amount) {
        log.debug("[DEBIT] Account: {}, Amount: {}", MaskUtil.maskAccount(account.getAccountNumber()), amount);
        return strategyFactory.executeTransaction(TransactionType.WITHDRAWAL, account, amount);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "#account.accountNumber")
    public TransactionStatusResponse phoneBillPayment(BankAccount account, PhoneCompany phoneCompany, String phoneNumber, double amount) {
        log.debug("[PHONE_BILL] Account: {}, PhoneCompany: {}, Phone: {}, Amount: {}", 
                MaskUtil.maskAccount(account.getAccountNumber()), phoneCompany, MaskUtil.maskPhone(phoneNumber), amount);
        return strategyFactory.executeTransaction(TransactionType.PHONE_BILL_PAYMENT, account, phoneCompany, phoneNumber, amount);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "#account.accountNumber")
    public TransactionStatusResponse checkPayment(BankAccount account, String payee, double amount) {
        log.debug("[CHECK_PAYMENT] Account: {}, Payee: {}, Amount: {}", 
                MaskUtil.maskAccount(account.getAccountNumber()), MaskUtil.maskPayee(payee), amount);
        return strategyFactory.executeTransaction(TransactionType.CHECK_PAYMENT, account, payee, amount);
    }
} 