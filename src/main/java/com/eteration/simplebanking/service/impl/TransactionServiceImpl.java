package com.eteration.simplebanking.service.impl;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.entity.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.domain.entity.transaction.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.domain.entity.transaction.CheckTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.repository.TransactionRepository;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    // TODO: Implement Strategy Pattern with TransactionType enum and TransactionStrategy interface
    @Override
    @Transactional
    public TransactionStatusResponse credit(BankAccount account, double amount) throws InsufficientBalanceException {
        return executeTransaction(account, amount, DepositTransaction::new, "Credit");
    }

    @Override
    @Transactional
    public TransactionStatusResponse debit(BankAccount account, double amount) throws InsufficientBalanceException {
        return executeTransaction(account, amount, WithdrawalTransaction::new, "Debit");
    }

    @Override
    @Transactional
    public TransactionStatusResponse phoneBillPayment(BankAccount account, PhoneCompany phoneCompany, String phoneNumber, double amount) throws InsufficientBalanceException {
        return executeComplexTransaction(account, amount, () -> new PhoneBillPaymentTransaction(phoneCompany, phoneNumber, amount), "PhoneBillPayment");
    }

    @Override
    @Transactional
    public TransactionStatusResponse checkPayment(BankAccount account, String payee, double amount) throws InsufficientBalanceException {
        return executeComplexTransaction(account, amount, () -> new CheckTransaction(payee, amount), "CheckPayment");
    }

    private TransactionStatusResponse executeTransaction(BankAccount account,
                                                         double amount,
                                                         Supplier<Transaction> transactionSupplier,
                                                         String operationType) throws InsufficientBalanceException {
        return executeComplexTransaction(account, amount, () -> {
            Transaction transaction = transactionSupplier.get();
            transaction.setAmount(amount);
            transaction.setDate(LocalDateTime.now());
            return transaction;
        }, operationType);
    }

    private TransactionStatusResponse executeComplexTransaction(BankAccount account,
                                                                double amount,
                                                                Supplier<Transaction> transactionSupplier,
                                                                String operationType) throws InsufficientBalanceException {
        try {
            String approvalCode = UUID.randomUUID().toString();
            Transaction transaction = transactionSupplier.get();

            transaction.setApprovalCode(approvalCode);

            account.post(transaction);
            transactionRepository.save(transaction);

            log.info("{} transaction successful: accountNumber={}, amount={}, approvalCode={}", operationType, account.getAccountNumber(), amount, approvalCode);

            return new TransactionStatusResponse("OK", approvalCode);

        } catch (InsufficientBalanceException e) {
            log.error("{} transaction failed - Insufficient balance: accountNumber={}, amount={}, error={}", operationType, account.getAccountNumber(), amount, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("{} transaction failed: accountNumber={}, amount={}, error={}", operationType, account.getAccountNumber(), amount, e.getMessage());
            throw new RuntimeException(operationType + " transaction failed: " + e.getMessage(), e);
        }
    }
} 