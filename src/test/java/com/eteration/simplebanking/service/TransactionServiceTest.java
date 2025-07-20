package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.repository.TransactionRepository;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private BankAccount testAccount;

    @BeforeEach
    void setUp() {
        testAccount = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();
    }

    @Test
    void credit_Success() {
        // Given
        double amount = 500.0;
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        // When
        TransactionStatusResponse result = transactionService.credit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        assertEquals(1500.0, testAccount.getBalance(), 0.001);
        assertEquals(1, testAccount.getTransactions().size());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void debit_Success() {
        // Given
        double amount = 300.0;
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        // When
        TransactionStatusResponse result = transactionService.debit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        assertEquals(700.0, testAccount.getBalance(), 0.001);
        assertEquals(1, testAccount.getTransactions().size());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void debit_InsufficientBalance_ThrowsException() {
        // Given
        double amount = 1500.0;

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.debit(testAccount, amount);
        });
        assertEquals(1000.0, testAccount.getBalance(), 0.001); // Balance should remain unchanged
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void phoneBillPayment_Success() {
        // Given
        PhoneCompany phoneCompany = PhoneCompany.COMPANY_A;
        String phoneNumber = "5423345566";
        double amount = 100.0;
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        // When
        TransactionStatusResponse result = transactionService.phoneBillPayment(testAccount, phoneCompany, phoneNumber, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        assertEquals(900.0, testAccount.getBalance(), 0.001);
        assertEquals(1, testAccount.getTransactions().size());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void phoneBillPayment_InsufficientBalance_ThrowsException() {
        // Given
        PhoneCompany phoneCompany = PhoneCompany.COMPANY_A;
        String phoneNumber = "5423345566";
        double amount = 1500.0;

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.phoneBillPayment(testAccount, phoneCompany, phoneNumber, amount);
        });
        assertEquals(1000.0, testAccount.getBalance(), 0.001); // Balance should remain unchanged
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void checkPayment_Success() {
        // Given
        String payee = "Test Payee";
        double amount = 200.0;
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        // When
        TransactionStatusResponse result = transactionService.checkPayment(testAccount, payee, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        assertEquals(800.0, testAccount.getBalance(), 0.001);
        assertEquals(1, testAccount.getTransactions().size());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void checkPayment_InsufficientBalance_ThrowsException() {
        // Given
        String payee = "Test Payee";
        double amount = 1500.0;

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.checkPayment(testAccount, payee, amount);
        });
        assertEquals(1000.0, testAccount.getBalance(), 0.001); // Balance should remain unchanged
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void credit_ZeroAmount_Success() {
        // Given
        double amount = 0.0;
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        // When
        TransactionStatusResponse result = transactionService.credit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertEquals(1000.0, testAccount.getBalance(), 0.001); // Balance should remain unchanged
        assertEquals(1, testAccount.getTransactions().size());
    }

    @Test
    void debit_ZeroAmount_Success() {
        // Given
        double amount = 0.0;
        when(transactionRepository.save(any(Transaction.class))).thenReturn(null);

        // When
        TransactionStatusResponse result = transactionService.debit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertEquals(1000.0, testAccount.getBalance(), 0.001); // Balance should remain unchanged
        assertEquals(1, testAccount.getTransactions().size());
    }
} 