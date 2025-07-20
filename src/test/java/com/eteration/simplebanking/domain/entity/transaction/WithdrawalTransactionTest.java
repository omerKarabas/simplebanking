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
        assertNotNull(withdrawalTransaction);
        assertEquals(0.0, withdrawalTransaction.getAmount(), 0.001);
        assertNull(withdrawalTransaction.getDate());
        assertNull(withdrawalTransaction.getAccount());
        assertNull(withdrawalTransaction.getApprovalCode());
    }

    @Test
    void testWithdrawalTransactionExecute() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = 500.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        withdrawalTransaction.execute(bankAccount);
        assertEquals(initialBalance - withdrawalAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithZeroAmount() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = 0.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        withdrawalTransaction.execute(bankAccount);
        assertEquals(initialBalance, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithInsufficientBalance() {
        double withdrawalAmount = 1500.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
        assertEquals(1000.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithExactBalance() throws InsufficientBalanceException {
        double withdrawalAmount = 1000.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        withdrawalTransaction.execute(bankAccount);
        assertEquals(0.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionExecuteWithLargeAmount() {
        double withdrawalAmount = 10000.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
        assertEquals(1000.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionSettersAndGetters() {
        double amount = 500.0;
        LocalDateTime date = LocalDateTime.now();
        String approvalCode = UUID.randomUUID().toString();
        withdrawalTransaction.setAmount(amount);
        withdrawalTransaction.setDate(date);
        withdrawalTransaction.setAccount(bankAccount);
        withdrawalTransaction.setApprovalCode(approvalCode);
        assertEquals(amount, withdrawalTransaction.getAmount(), 0.001);
        assertEquals(date, withdrawalTransaction.getDate());
        assertEquals(bankAccount, withdrawalTransaction.getAccount());
        assertEquals(approvalCode, withdrawalTransaction.getApprovalCode());
    }

    @Test
    void testWithdrawalTransactionEqualsAndHashCode() {
        WithdrawalTransaction transaction1 = new WithdrawalTransaction();
        WithdrawalTransaction transaction2 = new WithdrawalTransaction();
        WithdrawalTransaction transaction3 = new WithdrawalTransaction();
        transaction1.setId(1L);
        transaction2.setId(1L);
        transaction3.setId(2L);
        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
        assertNotEquals(transaction1, transaction3);
        assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

    @Test
    void testWithdrawalTransactionToString() {
        withdrawalTransaction.setId(1L);
        withdrawalTransaction.setAmount(500.0);
        withdrawalTransaction.setDate(LocalDateTime.now());
        String toString = withdrawalTransaction.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("WithdrawalTransaction"));
    }

    @Test
    void testWithdrawalTransactionWithNegativeAmount() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = -100.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        withdrawalTransaction.execute(bankAccount);
        assertEquals(initialBalance - withdrawalAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionMultipleExecutions() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount1 = 300.0;
        double withdrawalAmount2 = 200.0;
        withdrawalTransaction.setAmount(withdrawalAmount1);
        withdrawalTransaction.execute(bankAccount);
        WithdrawalTransaction withdrawalTransaction2 = new WithdrawalTransaction();
        withdrawalTransaction2.setAmount(withdrawalAmount2);
        withdrawalTransaction2.execute(bankAccount);
        assertEquals(initialBalance - withdrawalAmount1 - withdrawalAmount2, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionWithNullAccount() {
        withdrawalTransaction.setAmount(500.0);
        assertThrows(NullPointerException.class, () -> {
            withdrawalTransaction.execute(null);
        });
    }

    @Test
    void testWithdrawalTransactionWithDecimalAmount() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double withdrawalAmount = 123.45;
        withdrawalTransaction.setAmount(withdrawalAmount);
        withdrawalTransaction.execute(bankAccount);
        assertEquals(initialBalance - withdrawalAmount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionWithZeroBalance() {
        bankAccount.setBalance(0.0);
        double withdrawalAmount = 100.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
        assertEquals(0.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testWithdrawalTransactionWithSmallBalance() {
        bankAccount.setBalance(50.0);
        double withdrawalAmount = 100.0;
        withdrawalTransaction.setAmount(withdrawalAmount);
        assertThrows(InsufficientBalanceException.class, () -> {
            withdrawalTransaction.execute(bankAccount);
        });
    }
} 