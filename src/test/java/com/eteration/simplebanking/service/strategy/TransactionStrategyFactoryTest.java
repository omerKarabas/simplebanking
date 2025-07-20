package com.eteration.simplebanking.service.strategy;

import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.exception.StrategyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionStrategyFactoryTest {

    @Mock
    private TransactionStrategy depositStrategy;

    @Mock
    private TransactionStrategy withdrawalStrategy;

    @Mock
    private TransactionStrategy phoneBillStrategy;

    @Mock
    private TransactionStrategy checkStrategy;

    @Mock
    private com.eteration.simplebanking.domain.repository.TransactionRepository transactionRepository;

    private TransactionStrategyFactory factory;

    @BeforeEach
    void setUp() {
        // Setup mock strategies
        when(depositStrategy.getTransactionType()).thenReturn(TransactionType.DEPOSIT);
        when(withdrawalStrategy.getTransactionType()).thenReturn(TransactionType.WITHDRAWAL);
        when(phoneBillStrategy.getTransactionType()).thenReturn(TransactionType.PHONE_BILL_PAYMENT);
        when(checkStrategy.getTransactionType()).thenReturn(TransactionType.CHECK_PAYMENT);

        List<TransactionStrategy> strategies = Arrays.asList(
                depositStrategy, withdrawalStrategy, phoneBillStrategy, checkStrategy
        );

        factory = new TransactionStrategyFactory(strategies, transactionRepository);
    }

    @Test
    void getStrategy_Deposit_ReturnsCorrectStrategy() {
        // When
        TransactionStrategy result = factory.getStrategy(TransactionType.DEPOSIT);

        // Then
        assertNotNull(result);
        assertEquals(depositStrategy, result);
    }

    @Test
    void getStrategy_Withdrawal_ReturnsCorrectStrategy() {
        // When
        TransactionStrategy result = factory.getStrategy(TransactionType.WITHDRAWAL);

        // Then
        assertNotNull(result);
        assertEquals(withdrawalStrategy, result);
    }

    @Test
    void getStrategy_PhoneBillPayment_ReturnsCorrectStrategy() {
        // When
        TransactionStrategy result = factory.getStrategy(TransactionType.PHONE_BILL_PAYMENT);

        // Then
        assertNotNull(result);
        assertEquals(phoneBillStrategy, result);
    }

    @Test
    void getStrategy_CheckPayment_ReturnsCorrectStrategy() {
        // When
        TransactionStrategy result = factory.getStrategy(TransactionType.CHECK_PAYMENT);

        // Then
        assertNotNull(result);
        assertEquals(checkStrategy, result);
    }

    @Test
    void getStrategy_UnknownType_ThrowsException() {
        // Given
        TransactionType unknownType = TransactionType.DEPOSIT; // We'll use a known type but mock it as unknown

        // When & Then
        assertThrows(StrategyNotFoundException.class, () -> {
            // Create a new factory without the deposit strategy
            List<TransactionStrategy> strategiesWithoutDeposit = Arrays.asList(
                    withdrawalStrategy, phoneBillStrategy, checkStrategy
            );
            TransactionStrategyFactory factoryWithoutDeposit = new TransactionStrategyFactory(strategiesWithoutDeposit, transactionRepository);
            factoryWithoutDeposit.getStrategy(TransactionType.DEPOSIT);
        });
    }

    @Test
    void hasStrategy_ExistingStrategy_ReturnsTrue() {
        // When
        boolean result = factory.hasStrategy(TransactionType.DEPOSIT);

        // Then
        assertTrue(result);
    }

    @Test
    void hasStrategy_NonExistingStrategy_ReturnsFalse() {
        // Given
        List<TransactionStrategy> limitedStrategies = Arrays.asList(depositStrategy);
        TransactionStrategyFactory limitedFactory = new TransactionStrategyFactory(limitedStrategies, transactionRepository);

        // When
        boolean result = limitedFactory.hasStrategy(TransactionType.WITHDRAWAL);

        // Then
        assertFalse(result);
    }

    @Test
    void getAvailableTransactionTypes_ReturnsAllTypes() {
        // When
        var availableTypes = factory.getAvailableTransactionTypes();

        // Then
        assertEquals(4, availableTypes.size());
        assertTrue(availableTypes.contains(TransactionType.DEPOSIT));
        assertTrue(availableTypes.contains(TransactionType.WITHDRAWAL));
        assertTrue(availableTypes.contains(TransactionType.PHONE_BILL_PAYMENT));
        assertTrue(availableTypes.contains(TransactionType.CHECK_PAYMENT));
    }
} 