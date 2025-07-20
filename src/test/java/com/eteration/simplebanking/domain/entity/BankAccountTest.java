package com.eteration.simplebanking.domain.entity;

import com.eteration.simplebanking.domain.entity.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.entity.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.entity.transaction.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.domain.entity.transaction.CheckTransaction;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.eteration.simplebanking.TestConstants.*;
import static com.eteration.simplebanking.TestDataBuilder.*;

@ExtendWith(MockitoExtension.class)
class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = createTestAccount();
    }

    @Test
    void testBankAccountCreation() {
        assertNotNull(bankAccount);
        assertEquals(TEST_OWNER_NAME, bankAccount.getOwner());
        assertEquals(TEST_ACCOUNT_NUMBER, bankAccount.getAccountNumber());
        assertEquals(INITIAL_BALANCE, bankAccount.getBalance(), 0.001);
        assertNotNull(bankAccount.getTransactions());
        assertTrue(bankAccount.getTransactions().isEmpty());
    }

    @Test
    void testBankAccountBuilder() {
        BankAccount account = createTestAccountWithCustomData(TEST_ACCOUNT_NUMBER_4, TEST_OWNER_NAME_4, SMALL_BALANCE);

        assertEquals(TEST_OWNER_NAME_4, account.getOwner());
        assertEquals(TEST_ACCOUNT_NUMBER_4, account.getAccountNumber());
        assertEquals(SMALL_BALANCE, account.getBalance(), 0.001);
    }

    @Test
    void testBankAccountSettersAndGetters() {
        String newOwner = TEST_OWNER_NAME_3;
        String newAccountNumber = TEST_ACCOUNT_NUMBER_3;
        double newBalance = LARGE_BALANCE;
        List<Transaction> transactions = new ArrayList<>();

        bankAccount.setOwner(newOwner);
        bankAccount.setAccountNumber(newAccountNumber);
        bankAccount.setBalance(newBalance);
        bankAccount.setTransactions(transactions);

        assertEquals(newOwner, bankAccount.getOwner());
        assertEquals(newAccountNumber, bankAccount.getAccountNumber());
        assertEquals(newBalance, bankAccount.getBalance(), 0.001);
        assertEquals(transactions, bankAccount.getTransactions());
    }

    @Test
    void testCreditTransaction() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double creditAmount = MEDIUM_AMOUNT;

        bankAccount.credit(creditAmount);

        assertEquals(initialBalance + creditAmount, bankAccount.getBalance(), 0.001);
        assertEquals(1, bankAccount.getTransactions().size());
        
        Transaction transaction = bankAccount.getTransactions().get(0);
        assertInstanceOf(DepositTransaction.class, transaction);
        assertEquals(creditAmount, transaction.getAmount(), 0.001);
        assertEquals(bankAccount, transaction.getAccount());
    }

    @Test
    void testDebitTransaction() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double debitAmount = SMALL_AMOUNT;

        bankAccount.debit(debitAmount);

        assertEquals(initialBalance - debitAmount, bankAccount.getBalance(), 0.001);
        assertEquals(1, bankAccount.getTransactions().size());
        
        Transaction transaction = bankAccount.getTransactions().get(0);
        assertInstanceOf(WithdrawalTransaction.class, transaction);
        assertEquals(debitAmount, transaction.getAmount(), 0.001);
        assertEquals(bankAccount, transaction.getAccount());
    }

    @Test
    void testDebitTransactionWithInsufficientBalance() {
        double debitAmount = LARGE_AMOUNT;

        assertThrows(InsufficientBalanceException.class, () -> {
            bankAccount.debit(debitAmount);
        });
        
        assertEquals(INITIAL_BALANCE, bankAccount.getBalance(), 0.001);
        assertTrue(bankAccount.getTransactions().isEmpty());
    }

    @Test
    void testPayPhoneBill() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double amount = 100.0;
        PhoneCompany phoneCompany = PhoneCompany.COMPANY_A;
        String phoneNumber = "5551234567";

        bankAccount.payPhoneBill(phoneCompany, phoneNumber, amount);

        assertEquals(initialBalance - amount, bankAccount.getBalance(), 0.001);
        assertEquals(1, bankAccount.getTransactions().size());
        
        Transaction transaction = bankAccount.getTransactions().get(0);
        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals(bankAccount, transaction.getAccount());
    }

    @Test
    void testPayCheck() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();
        double amount = 200.0;
        String payee = "Test Payee";

        bankAccount.payCheck(payee, amount);

        assertEquals(initialBalance - amount, bankAccount.getBalance(), 0.001);
        assertEquals(1, bankAccount.getTransactions().size());
        
        Transaction transaction = bankAccount.getTransactions().get(0);
        assertEquals(amount, transaction.getAmount(), 0.001);
        assertEquals(bankAccount, transaction.getAccount());
    }

    @Test
    void testPostTransaction() throws InsufficientBalanceException {
        DepositTransaction transaction = createDepositTransaction(MEDIUM_AMOUNT);
        transaction.setDate(LocalDateTime.now());

        bankAccount.post(transaction);

        assertEquals(1, bankAccount.getTransactions().size());
        assertEquals(bankAccount, transaction.getAccount());
        assertTrue(bankAccount.getTransactions().contains(transaction));
    }

    @Test
    void testMultipleTransactions() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();

        bankAccount.credit(MEDIUM_AMOUNT);
        bankAccount.debit(200.0);
        bankAccount.credit(SMALL_AMOUNT);

        assertEquals(initialBalance + MEDIUM_AMOUNT - 200.0 + SMALL_AMOUNT, bankAccount.getBalance(), 0.001);
        assertEquals(3, bankAccount.getTransactions().size());
    }

    @Test
    void testBankAccountEqualsAndHashCode() {
        BankAccount account1 = createTestAccountWithCustomData(TEST_ACCOUNT_NUMBER, "Owner1", INITIAL_BALANCE);
        BankAccount account2 = createTestAccountWithCustomData(TEST_ACCOUNT_NUMBER, "Owner2", INITIAL_BALANCE);
        BankAccount account3 = createTestAccountWithCustomData(TEST_ACCOUNT_NUMBER, "Owner1", INITIAL_BALANCE);

        assertNotEquals(account1, account2);
        assertNotEquals(account1.hashCode(), account2.hashCode());

        assertEquals(account1, account3);
        assertEquals(account1.hashCode(), account3.hashCode());
    }

    @Test
    void testBankAccountToString() {
        String toString = bankAccount.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("owner=" + TEST_OWNER_NAME));
        assertTrue(toString.contains("accountNumber=" + TEST_ACCOUNT_NUMBER));
        assertTrue(toString.contains("balance=" + INITIAL_BALANCE));
    }

    @Test
    void testZeroAmountTransactions() throws InsufficientBalanceException {
        double initialBalance = bankAccount.getBalance();

        bankAccount.credit(ZERO_BALANCE);
        bankAccount.debit(ZERO_BALANCE);

        assertEquals(initialBalance, bankAccount.getBalance(), 0.001);
        assertEquals(2, bankAccount.getTransactions().size());
    }

    @Test
    void testNegativeBalanceNotAllowed() {
        bankAccount.setBalance(100.0);

        assertThrows(InsufficientBalanceException.class, () -> {
            bankAccount.debit(200.0);
        });
        
        assertEquals(100.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    void testCreateAccountAndSetBalance0() {
        BankAccount account = createTestAccountWithZeroBalance();
        assertEquals(TEST_OWNER_NAME_2, account.getOwner());
        assertEquals(TEST_ACCOUNT_NUMBER_2, account.getAccountNumber());
        assertEquals(ZERO_BALANCE, account.getBalance(), 0.001);
    }

    @Test
    void testDepositIntoBankAccount() {
        BankAccount account = createTestAccountWithCustomData("9834", "Demet Demircan", ZERO_BALANCE);
        try {
            account.credit(100);
            assertEquals(100.0, account.getBalance(), 0.001);
        } catch (InsufficientBalanceException e) {
            Assertions.fail("Credit should not throw InsufficientBalanceException");
        }
    }

    @Test
    void testWithdrawFromBankAccount() throws InsufficientBalanceException {
        BankAccount account = createTestAccountWithCustomData("9834", "Demet Demircan", ZERO_BALANCE);
        account.credit(100);
        assertEquals(100.0, account.getBalance(), 0.001);
        account.debit(50);
        assertEquals(50.0, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawException() {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            BankAccount account = createTestAccountWithCustomData("9834", "Demet Demircan", ZERO_BALANCE);
            account.credit(100);
            account.debit(500);
        });
    }
    
    @Test
    void testTransactions() throws InsufficientBalanceException {
        BankAccount account = createTestAccountWithCustomData("1234", "Canan Kaya", ZERO_BALANCE);
        assertEquals(0, account.getTransactions().size());

        DepositTransaction depositTrx = createDepositTransaction(100);
        account.post(depositTrx);

        WithdrawalTransaction withdrawalTrx = createWithdrawalTransaction(60);
        account.post(withdrawalTrx);
    }

    @Test
    void testPhoneBillPaymentTransaction() throws InsufficientBalanceException {
        BankAccount account = createTestAccountWithCustomData("1234", "Test User", ZERO_BALANCE);
        
        account.credit(100);
        assertEquals(100, account.getBalance(), 0.001);
        
        PhoneBillPaymentTransaction phoneBill = new PhoneBillPaymentTransaction(PhoneCompany.COMPANY_A, "5423345566", 96.50);
        account.post(phoneBill);
        assertEquals(3.50, account.getBalance(), 0.001);
        assertEquals(2, account.getTransactions().size());
    }

    @Test
    void testCheckTransaction() throws InsufficientBalanceException {
        BankAccount account = createTestAccountWithCustomData("1234", "Test User", ZERO_BALANCE);
        
        account.credit(100);
        assertEquals(100, account.getBalance(), 0.001);
        
        CheckTransaction check = new CheckTransaction("Test Payee", 50.0);
        account.post(check);
        assertEquals(50, account.getBalance(), 0.001);
        assertEquals(2, account.getTransactions().size());
        assertNotNull(check.getCheckNumber());
        assertFalse(check.getCheckNumber().isEmpty());
    }

    @Test
    void testComplexTransactionScenario() throws InsufficientBalanceException {
        BankAccount account = createTestAccountWithCustomData("12345", "Jim", ZERO_BALANCE);
        
        DepositTransaction deposit = createDepositTransaction(1000);
        account.post(deposit);
        
        WithdrawalTransaction withdrawal = createWithdrawalTransaction(200);
        account.post(withdrawal);
        
        account.post(new PhoneBillPaymentTransaction(PhoneCompany.COMPANY_A, "5423345566", 96.50));
        
        assertEquals(703.50, account.getBalance(), 0.0001);
        assertEquals(3, account.getTransactions().size());
    }
} 