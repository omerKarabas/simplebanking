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

    private static class TestTransaction extends Transaction {
        @Override
        public void execute(BankAccount account) throws InsufficientBalanceException {
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
        assertNotNull(transaction);
        assertEquals(0.0, transaction.getAmount(), 0.001);
        assertNull(transaction.getDate());
        assertNull(transaction.getAccount());
        assertNull(transaction.getApprovalCode());
    }

    @Test
    void testTransactionSettersAndGetters() {
        double amount = 500.0;
        LocalDateTime date = LocalDateTime.now();
        String approvalCode = UUID.randomUUID().toString();

        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setAccount(bankAccount);
        transaction.setApprovalCode(approvalCode);

        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals(date, transaction.getDate());
        assertEquals(bankAccount, transaction.getAccount());
        assertEquals(approvalCode, transaction.getApprovalCode());
    }

    @Test
    void testTransactionExecute() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double amount = 500.0;
        transaction.setAmount(amount);

        transaction.execute(bankAccount);

        assertEquals(initialBalance + amount, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testTransactionEqualsAndHashCode() {
        TestTransaction transaction1 = new TestTransaction();
        TestTransaction transaction2 = new TestTransaction();
        TestTransaction transaction3 = new TestTransaction();

        transaction1.setId(1L);
        transaction2.setId(1L);
        transaction3.setId(2L);

        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());

        assertNotEquals(transaction1, transaction3);
        assertNotEquals(transaction1.hashCode(), transaction3.hashCode());
    }

    @Test
    void testTransactionToString() {
        transaction.setId(1L);
        transaction.setAmount(500.0);
        transaction.setDate(LocalDateTime.now());

        String toString = transaction.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("Transaction"));
    }

    @Test
    void testTransactionWithNullValues() {
        TestTransaction nullTransaction = new TestTransaction();

        assertEquals(0.0, nullTransaction.getAmount(), 0.001);
        assertNull(nullTransaction.getDate());
        assertNull(nullTransaction.getAccount());
        assertNull(nullTransaction.getApprovalCode());
    }

    @Test
    void testTransactionAmountValidation() {
        double negativeAmount = -100.0;
        double zeroAmount = 0.0;
        double positiveAmount = 100.0;

        transaction.setAmount(negativeAmount);
        assertEquals(negativeAmount, transaction.getAmount(), 0.001);

        transaction.setAmount(zeroAmount);
        assertEquals(zeroAmount, transaction.getAmount(), 0.001);

        transaction.setAmount(positiveAmount);
        assertEquals(positiveAmount, transaction.getAmount(), 0.001);
    }

    @Test
    void testTransactionDateValidation() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

        transaction.setDate(pastDate);
        assertEquals(pastDate, transaction.getDate());

        transaction.setDate(currentDate);
        assertEquals(currentDate, transaction.getDate());

        transaction.setDate(futureDate);
        assertEquals(futureDate, transaction.getDate());
    }

    @Test
    void testTransactionApprovalCodeValidation() {
        String approvalCode1 = UUID.randomUUID().toString();
        String approvalCode2 = "APP-123";
        String approvalCode3 = null;

        transaction.setApprovalCode(approvalCode1);
        assertEquals(approvalCode1, transaction.getApprovalCode());

        transaction.setApprovalCode(approvalCode2);
        assertEquals(approvalCode2, transaction.getApprovalCode());

        transaction.setApprovalCode(approvalCode3);
        assertNull(transaction.getApprovalCode());
    }
} 