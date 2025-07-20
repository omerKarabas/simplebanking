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
class WithdrawalTransactionTest {

    private WithdrawalTransaction withdrawalTransaction;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        withdrawalTransaction = new WithdrawalTransaction();
        bankAccount = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();
    }

    @Test
    void testWithdrawalTransactionCreation() {
        // Then
        assertNotNull(withdrawalTransaction);
        assertEquals(0.0, withdrawalTransaction.getAmount(), 0.001);
        assertNull(withdrawalTransaction.getDate());
        assertNull(withdrawalTransaction.getAccount());
        assertNull(withdrawalTransaction.getApprovalCode());
    }

    @Test
    void testWithdrawalTransactionExecute() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = 500.0;
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When
        withdrawalTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance - withdrawalAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithZeroAmount() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = 0.0;
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When
        withdrawalTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithInsufficientBalance() {
        // Given
        double withdrawalAmount = 1500.0; // Mevcut bakiyeden fazla
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When & Then
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
        
        // Bakiye değişmemeli
        assertEquals(1000.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithExactBalance() throws InsufficientBalanceException {
        // Given
        double withdrawalAmount = 1000.0; // Tam bakiye
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When
        withdrawalTransaction.execute(bankAccount);

        // Then
        assertEquals(0.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithLargeAmount() {
        // Given
        double withdrawalAmount = 10000.0; // Çok büyük miktar
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When & Then
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
        
        // Bakiye değişmemeli
        assertEquals(1000.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionSettersAndGetters() {
        // Given
        double amount = 500.0;
        LocalDateTime date = LocalDateTime.now();
        String approvalCode = UUID.randomUUID().toString();

        // When
        withdrawalTransaction.setAmount(amount);
        withdrawalTransaction.setDate(date);
        withdrawalTransaction.setAccount(bankAccount);
        withdrawalTransaction.setApprovalCode(approvalCode);

        // Then
        assertEquals(amount, withdrawalTransaction.getAmount(), 0.001);
        assertEquals(date, withdrawalTransaction.getDate());
        assertEquals(bankAccount, withdrawalTransaction.getAccount());
        assertEquals(approvalCode, withdrawalTransaction.getApprovalCode());
    }

    @Test
    void testWithdrawalTransactionEqualsAndHashCode() {
        // Given
        WithdrawalTransaction transaction1 = new WithdrawalTransaction();
        WithdrawalTransaction transaction2 = new WithdrawalTransaction();
        WithdrawalTransaction transaction3 = new WithdrawalTransaction();

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
    void testWithdrawalTransactionToString() {
        // Given
        withdrawalTransaction.setId(1L);
        withdrawalTransaction.setAmount(500.0);
        withdrawalTransaction.setDate(LocalDateTime.now());

        // When
        String toString = withdrawalTransaction.toString();

        // Then
        assertNotNull(toString);
        // Lombok toString sadece sınıf adını içerir
        assertTrue(toString.contains("WithdrawalTransaction"));
    }

    @Test
    void testWithdrawalTransactionWithNegativeAmount() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = -100.0;
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When
        withdrawalTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance - withdrawalAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionMultipleExecutions() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount1 = 300.0;
        double withdrawalAmount2 = 200.0;

        // When
        withdrawalTransaction.setAmount(withdrawalAmount1);
        withdrawalTransaction.execute(bankAccount);

        WithdrawalTransaction withdrawalTransaction2 = new WithdrawalTransaction();
        withdrawalTransaction2.setAmount(withdrawalAmount2);
        withdrawalTransaction2.execute(bankAccount);

        // Then
        assertEquals(initialBalance - withdrawalAmount1 - withdrawalAmount2, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionWithNullAccount() {
        // Given
        withdrawalTransaction.setAmount(500.0);

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            withdrawalTransaction.execute(null);
        });
    }

    @Test
    void testWithdrawalTransactionWithDecimalAmount() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = 123.45;
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When
        withdrawalTransaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance - withdrawalAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionWithZeroBalance() {
        // Given
        bankAccount.setBalance(0.0);
        double withdrawalAmount = 100.0;
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When & Then
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
        
        assertEquals(0.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionWithSmallBalance() {
        // Given
        bankAccount.setBalance(50.0);
        double withdrawalAmount = 100.0;
        withdrawalTransaction.setAmount(withdrawalAmount);

        // When & Then
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
        
        assertEquals(50.0, bankAccount.getBalance(), 0.001);
    }
} 