package com.eteration.simplebanking.model.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TransactionRequest Tests")
class TransactionRequestTest {

    @Test
    @DisplayName("Should create TransactionRequest with positive amount")
    void shouldCreateTransactionRequestWithPositiveAmount() {
        // Given
        double amount = 100.50;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertEquals(amount, request.amount());
    }

    @Test
    @DisplayName("Should create TransactionRequest with zero amount")
    void shouldCreateTransactionRequestWithZeroAmount() {
        // Given
        double amount = 0.0;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertEquals(amount, request.amount());
    }

    @Test
    @DisplayName("Should create TransactionRequest with negative amount")
    void shouldCreateTransactionRequestWithNegativeAmount() {
        // Given
        double amount = -50.25;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertEquals(amount, request.amount());
    }

    @Test
    @DisplayName("Should create TransactionRequest with large amount")
    void shouldCreateTransactionRequestWithLargeAmount() {
        // Given
        double amount = 999999.99;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertEquals(amount, request.amount());
    }

    @Test
    @DisplayName("Should create TransactionRequest with decimal amount")
    void shouldCreateTransactionRequestWithDecimalAmount() {
        // Given
        double amount = 123.456789;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertEquals(amount, request.amount());
    }

    @Test
    @DisplayName("Should have correct record behavior")
    void shouldHaveCorrectRecordBehavior() {
        // Given
        double amount = 200.0;
        TransactionRequest request1 = new TransactionRequest(amount);
        TransactionRequest request2 = new TransactionRequest(amount);
        TransactionRequest request3 = new TransactionRequest(300.0);

        // When & Then
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
        
        String expectedToString = "TransactionRequest[amount=" + amount + "]";
        assertEquals(expectedToString, request1.toString());
    }

    @Test
    @DisplayName("Should handle maximum double value")
    void shouldHandleMaximumDoubleValue() {
        // Given
        double amount = Double.MAX_VALUE;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertEquals(amount, request.amount());
    }

    @Test
    @DisplayName("Should handle minimum double value")
    void shouldHandleMinimumDoubleValue() {
        // Given
        double amount = Double.MIN_VALUE;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertEquals(amount, request.amount());
    }

    @Test
    @DisplayName("Should handle NaN value")
    void shouldHandleNaNValue() {
        // Given
        double amount = Double.NaN;

        // When
        TransactionRequest request = new TransactionRequest(amount);

        // Then
        assertNotNull(request);
        assertTrue(Double.isNaN(request.amount()));
    }

    @Test
    @DisplayName("Should handle infinity values")
    void shouldHandleInfinityValues() {
        // Given
        double positiveInfinity = Double.POSITIVE_INFINITY;
        double negativeInfinity = Double.NEGATIVE_INFINITY;

        // When
        TransactionRequest positiveRequest = new TransactionRequest(positiveInfinity);
        TransactionRequest negativeRequest = new TransactionRequest(negativeInfinity);

        // Then
        assertNotNull(positiveRequest);
        assertNotNull(negativeRequest);
        assertEquals(positiveInfinity, positiveRequest.amount());
        assertEquals(negativeInfinity, negativeRequest.amount());
    }
} 