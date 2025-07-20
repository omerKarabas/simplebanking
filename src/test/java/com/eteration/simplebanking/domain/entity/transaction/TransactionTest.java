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
class TransactionTest {

    // Transaction abstract olduğu için test için concrete bir sınıf oluşturuyoruz
    private static class TestTransaction extends Transaction {
        @Override
        public void execute(BankAccount account) throws InsufficientBalanceException {
            // Test implementation
            account.setBalance(account.getBalance() + this.amount);
        }
    }

    private TestTransaction transaction;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        transaction = new TestTransaction();
        bankAccount = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();
    }

    @Test
    void testTransactionCreation() {
        // Then
        assertNotNull(transaction);
        assertEquals(0.0, transaction.getAmount(), 0.001);
        assertNull(transaction.getDate());
        assertNull(transaction.getAccount());
        assertNull(transaction.getApprovalCode());
    }

    @Test
    void testTransactionSettersAndGetters() {
        // Given
        double amount = 500.0;
        LocalDateTime date = LocalDateTime.now();
        String approvalCode = UUID.randomUUID().toString();

        // When
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setAccount(bankAccount);
        transaction.setApprovalCode(approvalCode);

        // Then
        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals(date, transaction.getDate());
        assertEquals(bankAccount, transaction.getAccount());
        assertEquals(approvalCode, transaction.getApprovalCode());
    }

    @Test
    void testTransactionExecute() throws InsufficientBalanceException {
        // Given
        double initialBalance = bankAccount.getBalance();
        double amount = 500.0;
        transaction.setAmount(amount);

        // When
        transaction.execute(bankAccount);

        // Then
        assertEquals(initialBalance + amount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testTransactionEqualsAndHashCode() {
        // Given
        TestTransaction transaction1 = new TestTransaction();
        TestTransaction transaction2 = new TestTransaction();
        TestTransaction transaction3 = new TestTransaction();

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
    void testTransactionToString() {
        // Given
        transaction.setId(1L);
        transaction.setAmount(500.0);
        transaction.setDate(LocalDateTime.now());

        // When
        String toString = transaction.toString();

        // Then
        assertNotNull(toString);
        // Lombok toString sadece sınıf adını içerir
        assertTrue(toString.contains("Transaction"));
    }

    @Test
    void testTransactionWithNullValues() {
        // Given
        TestTransaction nullTransaction = new TestTransaction();

        // When & Then
        assertEquals(0.0, nullTransaction.getAmount(), 0.001);
        assertNull(nullTransaction.getDate());
        assertNull(nullTransaction.getAccount());
        assertNull(nullTransaction.getApprovalCode());
    }

    @Test
    void testTransactionAmountValidation() {
        // Given
        double negativeAmount = -100.0;
        double zeroAmount = 0.0;
        double positiveAmount = 100.0;

        // When & Then
        // Negative amount test
        transaction.setAmount(negativeAmount);
        assertEquals(negativeAmount, transaction.getAmount(), 0.001);

        // Zero amount test
        transaction.setAmount(zeroAmount);
        assertEquals(zeroAmount, transaction.getAmount(), 0.001);

        // Positive amount test
        transaction.setAmount(positiveAmount);
        assertEquals(positiveAmount, transaction.getAmount(), 0.001);
    }

    @Test
    void testTransactionDateValidation() {
        // Given
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

        // When & Then
        transaction.setDate(pastDate);
        assertEquals(pastDate, transaction.getDate());

        transaction.setDate(currentDate);
        assertEquals(currentDate, transaction.getDate());

        transaction.setDate(futureDate);
        assertEquals(futureDate, transaction.getDate());
    }

    @Test
    void testTransactionApprovalCodeValidation() {
        // Given
        String approvalCode1 = UUID.randomUUID().toString();
        String approvalCode2 = "APP-123";
        String approvalCode3 = null;

        // When & Then
        transaction.setApprovalCode(approvalCode1);
        assertEquals(approvalCode1, transaction.getApprovalCode());

        transaction.setApprovalCode(approvalCode2);
        assertEquals(approvalCode2, transaction.getApprovalCode());

        transaction.setApprovalCode(approvalCode3);
        assertNull(transaction.getApprovalCode());
    }
} 