package com.eteration.simplebanking.service.core;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.interfaces.TransactionService;
import com.eteration.simplebanking.service.strategy.TransactionStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for transaction operations.
 * This service delegates transaction execution to the TransactionStrategyFactory
 * to maintain proper separation of concerns.
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionStrategyFactory strategyFactory;

    @Override
    @Transactional
    public TransactionStatusResponse credit(BankAccount account, double amount) {
        return strategyFactory.executeTransaction(TransactionType.DEPOSIT, account, amount);
    }

    @Override
    @Transactional
    public TransactionStatusResponse debit(BankAccount account, double amount) {
        return strategyFactory.executeTransaction(TransactionType.WITHDRAWAL, account, amount);
    }

    @Override
    @Transactional
    public TransactionStatusResponse phoneBillPayment(BankAccount account, PhoneCompany phoneCompany, String phoneNumber, double amount) {
        return strategyFactory.executeTransaction(TransactionType.PHONE_BILL_PAYMENT, account, phoneCompany, phoneNumber, amount);
    }

    @Override
    @Transactional
    public TransactionStatusResponse checkPayment(BankAccount account, String payee, double amount) {
        return strategyFactory.executeTransaction(TransactionType.CHECK_PAYMENT, account, payee, amount);
    }
} 