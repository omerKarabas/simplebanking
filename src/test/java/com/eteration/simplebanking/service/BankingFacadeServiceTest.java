package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.interfaces.BankingFacadeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
})
@Transactional
class BankingFacadeServiceTest {

    @Autowired
    private BankingFacadeService bankingFacadeService;

    private String generateUniqueAccountNumber() {
        return "TEST_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    void createBankAccount_Success() {
        String owner = "Test Owner";
        String accountNumber = generateUniqueAccountNumber();

        BankAccountResponse result = bankingFacadeService.createBankAccount(owner, accountNumber);

        assertNotNull(result);
        assertEquals(accountNumber, result.accountNumber());
        assertEquals(owner, result.owner());
        assertEquals(0.0, result.balance());
    }

    @Test
    void getBankAccount_Success() {
        String owner = "Test Owner";
        String accountNumber = generateUniqueAccountNumber();
        
        // First create an account
        bankingFacadeService.createBankAccount(owner, accountNumber);
        
        // Then get the account
        BankAccountResponse result = bankingFacadeService.getBankAccount(accountNumber);

        assertNotNull(result);
        assertEquals(accountNumber, result.accountNumber());
        assertEquals(owner, result.owner());
    }

    @Test
    void credit_Success() {
        String accountNumber = generateUniqueAccountNumber();
        double amount = 500.0;
        
        // First create an account
        bankingFacadeService.createBankAccount("Test Owner", accountNumber);
        
        // Then credit
        TransactionStatusResponse result = bankingFacadeService.credit(accountNumber, amount);

        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
    }

    @Test
    void debit_Success() {
        String accountNumber = generateUniqueAccountNumber();
        double amount = 300.0;
        
        // First create an account and credit it
        bankingFacadeService.createBankAccount("Test Owner", accountNumber);
        bankingFacadeService.credit(accountNumber, 1000.0);
        
        // Then debit
        TransactionStatusResponse result = bankingFacadeService.debit(accountNumber, amount);

        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
    }

    @Test
    void credit_AccountNotFound_ThrowsException() {
        String accountNumber = "99999";
        double amount = 500.0;

        assertThrows(AccountNotFoundException.class, () -> {
            bankingFacadeService.credit(accountNumber, amount);
        });
    }

    @Test
    void debit_AccountNotFound_ThrowsException() {
        String accountNumber = "99999";
        double amount = 300.0;

        assertThrows(AccountNotFoundException.class, () -> {
            bankingFacadeService.debit(accountNumber, amount);
        });
    }

    @Test
    void debit_InsufficientBalance_ThrowsException() {
        String accountNumber = generateUniqueAccountNumber();
        double amount = 1500.0;
        
        // First create an account and credit it with less than the debit amount
        bankingFacadeService.createBankAccount("Test Owner", accountNumber);
        bankingFacadeService.credit(accountNumber, 1000.0);
        
        // Then try to debit more than available
        assertThrows(RuntimeException.class, () -> {
            bankingFacadeService.debit(accountNumber, amount);
        });
    }

    @Test
    void credit_MinimumAmount_Success() {
        String accountNumber = generateUniqueAccountNumber();
        double amount = 0.01; // Minimum allowed amount
        
        // First create an account
        bankingFacadeService.createBankAccount("Test Owner", accountNumber);
        
        // Then credit minimum amount
        TransactionStatusResponse result = bankingFacadeService.credit(accountNumber, amount);

        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
    }

    @Test
    void debit_MinimumAmount_Success() {
        String accountNumber = generateUniqueAccountNumber();
        double amount = 0.01; // Minimum allowed amount
        
        // First create an account and credit it
        bankingFacadeService.createBankAccount("Test Owner", accountNumber);
        bankingFacadeService.credit(accountNumber, 1000.0);
        
        // Then debit minimum amount
        TransactionStatusResponse result = bankingFacadeService.debit(accountNumber, amount);

        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
    }
} 