package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.impl.BankingFacadeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankingFacadeServiceTest {

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private BankingFacadeServiceImpl bankingFacadeService;

    private BankAccount testAccount;
    private BankAccountResponse testAccountResponse;
    private TransactionStatusResponse testTransactionResponse;

    @BeforeEach
    void setUp() {
        testAccount = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();

        testAccountResponse = new BankAccountResponse(
                "12345",
                "Test Owner",
                1000.0,
                LocalDateTime.now(),
                null
        );

        testTransactionResponse = new TransactionStatusResponse("OK", "test-approval-code");
    }

    @Test
    void createBankAccount_Success() {
        String owner = "Test Owner";
        String accountNumber = "12345";
        when(bankAccountService.createAccount(owner, accountNumber)).thenReturn(testAccountResponse);

        BankAccountResponse result = bankingFacadeService.createBankAccount(owner, accountNumber);

        assertNotNull(result);
        assertEquals(testAccountResponse, result);
        verify(bankAccountService).createAccount(owner, accountNumber);
    }

    @Test
    void getBankAccount_Success() {
        String accountNumber = "12345";
        when(bankAccountService.getAccount(accountNumber)).thenReturn(testAccountResponse);

        BankAccountResponse result = bankingFacadeService.getBankAccount(accountNumber);

        assertNotNull(result);
        assertEquals(testAccountResponse, result);
        verify(bankAccountService).getAccount(accountNumber);
    }

    @Test
    void credit_Success() {
        String accountNumber = "12345";
        double amount = 500.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenReturn(testAccount);
        when(transactionService.credit(testAccount, amount)).thenReturn(testTransactionResponse);
        doNothing().when(bankAccountService).saveAccount(testAccount);

        TransactionStatusResponse result = bankingFacadeService.credit(accountNumber, amount);

        assertNotNull(result);
        assertEquals(testTransactionResponse, result);
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService).credit(testAccount, amount);
        verify(bankAccountService).saveAccount(testAccount);
    }

    @Test
    void debit_Success() {
        String accountNumber = "12345";
        double amount = 300.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenReturn(testAccount);
        when(transactionService.debit(testAccount, amount)).thenReturn(testTransactionResponse);
        doNothing().when(bankAccountService).saveAccount(testAccount);

        TransactionStatusResponse result = bankingFacadeService.debit(accountNumber, amount);

        assertNotNull(result);
        assertEquals(testTransactionResponse, result);
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService).debit(testAccount, amount);
        verify(bankAccountService).saveAccount(testAccount);
    }

    @Test
    void credit_AccountNotFound_ThrowsException() {
        String accountNumber = "99999";
        double amount = 500.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenThrow(new AccountNotFoundException("Account not found"));

        assertThrows(AccountNotFoundException.class, () -> {
            bankingFacadeService.credit(accountNumber, amount);
        });
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService, never()).credit(any(), anyDouble());
        verify(bankAccountService, never()).saveAccount(any());
    }

    @Test
    void debit_AccountNotFound_ThrowsException() {
        String accountNumber = "99999";
        double amount = 300.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenThrow(new AccountNotFoundException("Account not found"));

        assertThrows(AccountNotFoundException.class, () -> {
            bankingFacadeService.debit(accountNumber, amount);
        });
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService, never()).debit(any(), anyDouble());
        verify(bankAccountService, never()).saveAccount(any());
    }

    @Test
    void credit_InsufficientBalance_ThrowsException() {
        String accountNumber = "12345";
        double amount = 1500.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenReturn(testAccount);
        when(transactionService.credit(testAccount, amount)).thenThrow(new RuntimeException("Insufficient balance"));

        assertThrows(RuntimeException.class, () -> {
            bankingFacadeService.credit(accountNumber, amount);
        });
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService).credit(testAccount, amount);
        verify(bankAccountService, never()).saveAccount(any());
    }

    @Test
    void debit_InsufficientBalance_ThrowsException() {
        String accountNumber = "12345";
        double amount = 1500.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenReturn(testAccount);
        when(transactionService.debit(testAccount, amount)).thenThrow(new RuntimeException("Insufficient balance"));

        assertThrows(RuntimeException.class, () -> {
            bankingFacadeService.debit(accountNumber, amount);
        });
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService).debit(testAccount, amount);
        verify(bankAccountService, never()).saveAccount(any());
    }

    @Test
    void credit_ZeroAmount_Success() {
        String accountNumber = "12345";
        double amount = 0.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenReturn(testAccount);
        when(transactionService.credit(testAccount, amount)).thenReturn(testTransactionResponse);
        doNothing().when(bankAccountService).saveAccount(testAccount);

        TransactionStatusResponse result = bankingFacadeService.credit(accountNumber, amount);

        assertNotNull(result);
        assertEquals(testTransactionResponse, result);
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService).credit(testAccount, amount);
        verify(bankAccountService).saveAccount(testAccount);
    }

    @Test
    void debit_ZeroAmount_Success() {
        String accountNumber = "12345";
        double amount = 0.0;
        when(bankAccountService.findAccountByNumber(accountNumber)).thenReturn(testAccount);
        when(transactionService.debit(testAccount, amount)).thenReturn(testTransactionResponse);
        doNothing().when(bankAccountService).saveAccount(testAccount);

        TransactionStatusResponse result = bankingFacadeService.debit(accountNumber, amount);

        assertNotNull(result);
        assertEquals(testTransactionResponse, result);
        verify(bankAccountService).findAccountByNumber(accountNumber);
        verify(transactionService).debit(testAccount, amount);
        verify(bankAccountService).saveAccount(testAccount);
    }
} 