package com.eteration.simplebanking.service.strategy.strategy;

import com.eteration.simplebanking.domain.entity.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.service.strategy.strategy.DepositTransactionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepositTransactionStrategyTest {

    private DepositTransactionStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new DepositTransactionStrategy();
    }

    @Test
    void createTransaction_ValidAmount_CreatesDepositTransaction() {
        // Given
        double amount = 100.0;

        // When
        Transaction result = strategy.createTransaction(amount);

        // Then
        assertNotNull(result);
        assertInstanceOf(DepositTransaction.class, result);
        assertEquals(amount, result.getAmount(), 0.001);
        assertNotNull(result.getDate());
    }

    @Test
    void createTransaction_ZeroAmount_CreatesTransaction() {
        // Given
        double amount = 0.0;

        // When
        Transaction result = strategy.createTransaction(amount);

        // Then
        assertNotNull(result);
        assertInstanceOf(DepositTransaction.class, result);
        assertEquals(amount, result.getAmount(), 0.001);
    }

    @Test
    void createTransaction_NullParameter_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            strategy.createTransaction((Object) null);
        });
    }

    @Test
    void createTransaction_WrongParameterType_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            strategy.createTransaction("invalid");
        });
    }

    @Test
    void createTransaction_NoParameters_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            strategy.createTransaction();
        });
    }

    @Test
    void getTransactionType_ReturnsDeposit() {
        // When
        TransactionType result = strategy.getTransactionType();

        // Then
        assertEquals(TransactionType.DEPOSIT, result);
    }

    @Test
    void getOperationType_ReturnsCredit() {
        // When
        String result = strategy.getOperationType();

        // Then
        assertEquals("Credit", result);
    }
} 