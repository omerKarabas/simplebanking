package com.eteration.simplebanking.domain.entity.transaction;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepositTransactionTest {

    private DepositTransaction depositTransaction;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        depositTransaction = new DepositTransaction();
        bankAccount = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();
    }

    @Test
    void testDepositTransactionCreation() {
        // Then
        assertNotNull(depositTransaction);
        assertEquals(0.0, depositTransaction.getAmount(), 0.001);
        assertNull(depositTransaction.getDate());
        assertNull(depositTransaction.getAccount());
        assertNull(depositTransaction.getApprovalCode());
    }

    @Test
    void testDepositTransactionExecute() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double depositAmount = 500.0;
        depositTransaction.setAmount(depositAmount);

        // When
        depositTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance + depositAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testDepositTransactionExecuteWithZeroAmount() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double depositAmount = 0.0;
        depositTransaction.setAmount(depositAmount);

        // When
        depositTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testDepositTransactionExecuteWithLargeAmount() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double depositAmount = 10000.0;
        depositTransaction.setAmount(depositAmount);

        // When
        depositTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance + depositAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testDepositTransactionSettersAndGetters() {
        // Given
        double amount = 500.0;
        LocalDateTime date = LocalDateTime.now();
        String approvalCode = UUID.randomUUID().toString();

        // When
        depositTransaction.setAmount(amount);
        depositTransaction.setDate(date);
        depositTransaction.setAccount(bankAccount);
        depositTransaction.setApprovalCode(approvalCode);

        // Then
        assertEquals(amount, depositTransaction.getAmount(), 0.001);
        assertEquals(date, depositTransaction.getDate());
        assertEquals(bankAccount, depositTransaction.getAccount());
        assertEquals(approvalCode, depositTransaction.getApprovalCode());
    }

    @Test
    void testDepositTransactionEqualsAndHashCode() {
        // Given
        DepositTransaction transaction1 = new DepositTransaction();
        DepositTransaction transaction2 = new DepositTransaction();
        DepositTransaction transaction3 = new DepositTransaction();

        transaction1.setId(1L);
        transaction2.setId(1L);
        transaction3.setId(2L);

        // When & Then
        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());

        assertNotEquals(transaction1, transaction3);
        assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

    @Test
    void testDepositTransactionToString() {
        // Given
        depositTransaction.setId(1L);
        depositTransaction.setAmount(500.0);
        depositTransaction.setDate(LocalDateTime.now());

        // When
        String toString = depositTransaction.toString();

        // Then
        assertNotNull(toString);
        // Lombok toString sadece sınıf adını içerir
        assertTrue(toString.contains("DepositTransaction"));
    }

    @Test
    void testDepositTransactionWithNegativeAmount() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double depositAmount = -100.0;
        depositTransaction.setAmount(depositAmount);

        // When
        depositTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance + depositAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testDepositTransactionMultipleExecutions() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double depositAmount1 = 500.0;
        double depositAmount2 = 300.0;

        // When
        depositTransaction.setAmount(depositAmount1);
        depositTransaction.execute(bankAccount);

        DepositTransaction depositTransaction2 = new DepositTransaction();
        depositTransaction2.setAmount(depositAmount2);
        depositTransaction2.execute(bankAccount);

        // Then
        assertEquals(initialBalance + depositAmount1 + depositAmount2, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testDepositTransactionWithNullAccount() {
        // Given
        depositTransaction.setAmount(500.0);

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            depositTransaction.execute(null);
        });
    }

    @Test
    void testDepositTransactionWithDecimalAmount() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double depositAmount = 123.45;
        depositTransaction.setAmount(depositAmount);

        // When
        depositTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance + depositAmount, bankAccount.getBalance(), 0.001);
    }
} 