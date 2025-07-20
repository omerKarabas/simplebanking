package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.PhoneCompany;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.domain.repository.TransactionRepository;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.service.core.TransactionServiceImpl;
import com.eteration.simplebanking.service.strategy.TransactionStrategy;
import com.eteration.simplebanking.service.strategy.TransactionStrategyFactory;
import com.eteration.simplebanking.util.SecureMaskUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionStrategyFactory strategyFactory;

    @Mock
    private SecureMaskUtil secureMaskUtil;

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

        // Mock SecureMaskUtil methods
        lenient().when(secureMaskUtil.maskAccount(anyString())).thenReturn("****12345");
        lenient().when(secureMaskUtil.maskPhone(anyString())).thenReturn("****5566");
        lenient().when(secureMaskUtil.maskApprovalCode(anyString())).thenReturn("****1234");
        lenient().when(secureMaskUtil.maskName(anyString())).thenReturn("T***");
        lenient().when(secureMaskUtil.maskPayee(anyString())).thenReturn("T***");
    }

    @Test
    void credit_Success() {
        // Given
        double amount = 500.0;
        TransactionStatusResponse expectedResponse = new TransactionStatusResponse("OK", "test-approval-code");
        when(strategyFactory.executeTransaction(TransactionType.DEPOSIT, testAccount, amount))
                .thenReturn(expectedResponse);

        // When
        TransactionStatusResponse result = transactionService.credit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        verify(strategyFactory).executeTransaction(TransactionType.DEPOSIT, testAccount, amount);
    }

    @Test
    void debit_Success() {
        // Given
        double amount = 300.0;
        TransactionStatusResponse expectedResponse = new TransactionStatusResponse("OK", "test-approval-code");
        when(strategyFactory.executeTransaction(TransactionType.WITHDRAWAL, testAccount, amount))
                .thenReturn(expectedResponse);

        // When
        TransactionStatusResponse result = transactionService.debit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        verify(strategyFactory).executeTransaction(TransactionType.WITHDRAWAL, testAccount, amount);
    }

    @Test
    void debit_InsufficientBalance_ThrowsException() {
        // Given
        double amount = 1500.0;
        when(strategyFactory.executeTransaction(TransactionType.WITHDRAWAL, testAccount, amount))
                .thenThrow(new RuntimeException("Debit transaction failed: Insufficient balance"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.debit(testAccount, amount);
        });
        verify(strategyFactory).executeTransaction(TransactionType.WITHDRAWAL, testAccount, amount);
    }

    @Test
    void phoneBillPayment_Success() {
        // Given
        PhoneCompany phoneCompany = PhoneCompany.COMPANY_A;
        String phoneNumber = "5423345566";
        double amount = 100.0;
        TransactionStatusResponse expectedResponse = new TransactionStatusResponse("OK", "test-approval-code");
        when(strategyFactory.executeTransaction(TransactionType.PHONE_BILL_PAYMENT, testAccount, phoneCompany, phoneNumber, amount))
                .thenReturn(expectedResponse);

        // When
        TransactionStatusResponse result = transactionService.phoneBillPayment(testAccount, phoneCompany, phoneNumber, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        verify(strategyFactory).executeTransaction(TransactionType.PHONE_BILL_PAYMENT, testAccount, phoneCompany, phoneNumber, amount);
    }

    @Test
    void phoneBillPayment_InsufficientBalance_ThrowsException() {
        // Given
        PhoneCompany phoneCompany = PhoneCompany.COMPANY_A;
        String phoneNumber = "5423345566";
        double amount = 1500.0;
        when(strategyFactory.executeTransaction(TransactionType.PHONE_BILL_PAYMENT, testAccount, phoneCompany, phoneNumber, amount))
                .thenThrow(new RuntimeException("PhoneBillPayment transaction failed: Insufficient balance"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.phoneBillPayment(testAccount, phoneCompany, phoneNumber, amount);
        });
        verify(strategyFactory).executeTransaction(TransactionType.PHONE_BILL_PAYMENT, testAccount, phoneCompany, phoneNumber, amount);
    }

    @Test
    void checkPayment_Success() {
        // Given
        String payee = "Test Payee";
        double amount = 200.0;
        TransactionStatusResponse expectedResponse = new TransactionStatusResponse("OK", "test-approval-code");
        when(strategyFactory.executeTransaction(TransactionType.CHECK_PAYMENT, testAccount, payee, amount))
                .thenReturn(expectedResponse);

        // When
        TransactionStatusResponse result = transactionService.checkPayment(testAccount, payee, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        assertNotNull(result.approvalCode());
        verify(strategyFactory).executeTransaction(TransactionType.CHECK_PAYMENT, testAccount, payee, amount);
    }

    @Test
    void checkPayment_InsufficientBalance_ThrowsException() {
        // Given
        String payee = "Test Payee";
        double amount = 1500.0;
        when(strategyFactory.executeTransaction(TransactionType.CHECK_PAYMENT, testAccount, payee, amount))
                .thenThrow(new RuntimeException("CheckPayment transaction failed: Insufficient balance"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.checkPayment(testAccount, payee, amount);
        });
        verify(strategyFactory).executeTransaction(TransactionType.CHECK_PAYMENT, testAccount, payee, amount);
    }

    @Test
    void credit_ZeroAmount_Success() {
        // Given
        double amount = 0.0;
        TransactionStatusResponse expectedResponse = new TransactionStatusResponse("OK", "test-approval-code");
        when(strategyFactory.executeTransaction(TransactionType.DEPOSIT, testAccount, amount))
                .thenReturn(expectedResponse);

        // When
        TransactionStatusResponse result = transactionService.credit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        verify(strategyFactory).executeTransaction(TransactionType.DEPOSIT, testAccount, amount);
    }

    @Test
    void debit_ZeroAmount_Success() {
        // Given
        double amount = 0.0;
        TransactionStatusResponse expectedResponse = new TransactionStatusResponse("OK", "test-approval-code");
        when(strategyFactory.executeTransaction(TransactionType.WITHDRAWAL, testAccount, amount))
                .thenReturn(expectedResponse);

        // When
        TransactionStatusResponse result = transactionService.debit(testAccount, amount);

        // Then
        assertNotNull(result);
        assertEquals("OK", result.status());
        verify(strategyFactory).executeTransaction(TransactionType.WITHDRAWAL, testAccount, amount);
    }
    
} 