package com.eteration.simplebanking.model.mapper;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.entity.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountMapperTest {

    private BankAccountMapper mapper;
    private BankAccount testAccount;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        mapper = new BankAccountMapper();
        testDateTime = LocalDateTime.now();
        
        testAccount = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();
    }

    @Test
    void toAccountResponse_Success() {
        DepositTransaction depositTransaction = new DepositTransaction();
        depositTransaction.setAmount(500.0);
        depositTransaction.setDate(testDateTime);
        depositTransaction.setApprovalCode("DEP-001");

        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
        withdrawalTransaction.setAmount(200.0);
        withdrawalTransaction.setDate(testDateTime);
        withdrawalTransaction.setApprovalCode("WIT-001");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(depositTransaction);
        transactions.add(withdrawalTransaction);
        testAccount.setTransactions(transactions);

        BankAccountResponse result = mapper.toAccountResponse(testAccount);

        assertNotNull(result);
        assertEquals("12345", result.accountNumber());
        assertEquals("Test Owner", result.owner());
        assertEquals(1000.0, result.balance(), 0.001);
        assertNotNull(result.transactions());
        assertEquals(2, result.transactions().size());
    }

    @Test
    void toAccountResponse_EmptyTransactions() {
        testAccount.setTransactions(new ArrayList<>());

        BankAccountResponse result = mapper.toAccountResponse(testAccount);

        assertNotNull(result);
        assertEquals("12345", result.accountNumber());
        assertEquals("Test Owner", result.owner());
        assertEquals(1000.0, result.balance(), 0.001);
        assertNotNull(result.transactions());
        assertEquals(0, result.transactions().size());
    }

    @Test
    void toAccountResponse_NullTransactions() {
        testAccount.setTransactions(null);

        BankAccountResponse result = mapper.toAccountResponse(testAccount);

        assertNotNull(result);
        assertEquals("12345", result.accountNumber());
        assertEquals("Test Owner", result.owner());
        assertEquals(1000.0, result.balance(), 0.001);
        assertNull(result.transactions());
    }

    @Test
    void toTransactionResponse_DepositTransaction() {
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(500.0);
        transaction.setDate(testDateTime);
        transaction.setApprovalCode("DEP-001");

        TransactionResponse result = mapper.toTransactionResponse(transaction);

        assertNotNull(result);
        assertEquals(testDateTime, result.date());
        assertEquals(500.0, result.amount(), 0.001);
        assertEquals("DepositTransaction", result.type());
        assertEquals("DEP-001", result.approvalCode());
    }

    @Test
    void toTransactionResponse_WithdrawalTransaction() {
        WithdrawalTransaction transaction = new WithdrawalTransaction();
        transaction.setAmount(200.0);
        transaction.setDate(testDateTime);
        transaction.setApprovalCode("WIT-001");

        TransactionResponse result = mapper.toTransactionResponse(transaction);

        assertNotNull(result);
        assertEquals(testDateTime, result.date());
        assertEquals(200.0, result.amount(), 0.001);
        assertEquals("WithdrawalTransaction", result.type());
        assertEquals("WIT-001", result.approvalCode());
    }

    @Test
    void toTransactionResponse_ZeroAmount() {
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(0.0);
        transaction.setDate(testDateTime);
        transaction.setApprovalCode("ZERO-001");

        TransactionResponse result = mapper.toTransactionResponse(transaction);

        assertNotNull(result);
        assertEquals(testDateTime, result.date());
        assertEquals(0.0, result.amount(), 0.001);
        assertEquals("DepositTransaction", result.type());
        assertEquals("ZERO-001", result.approvalCode());
    }

    @Test
    void toTransactionResponse_NullApprovalCode() {
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(100.0);
        transaction.setDate(testDateTime);
        transaction.setApprovalCode(null);

        TransactionResponse result = mapper.toTransactionResponse(transaction);

        assertNotNull(result);
        assertEquals(testDateTime, result.date());
        assertEquals(100.0, result.amount(), 0.001);
        assertEquals("DepositTransaction", result.type());
        assertNull(result.approvalCode());
    }
} 