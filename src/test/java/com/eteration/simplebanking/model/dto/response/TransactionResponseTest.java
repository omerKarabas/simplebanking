package com.eteration.simplebanking.model.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TransactionResponse Tests")
class TransactionResponseTest {

    @Test
    @DisplayName("Should create TransactionResponse with valid data")
    void shouldCreateTransactionResponseWithValidData() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = 100.50;
        String type = "DEPOSIT";
        String approvalCode = "ABC123";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(date, response.date());
        assertEquals(amount, response.amount());
        assertEquals(type, response.type());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should create TransactionResponse with null values")
    void shouldCreateTransactionResponseWithNullValues() {
        // Given & When
        TransactionResponse response = new TransactionResponse(null, 0.0, null, null);

        // Then
        assertNotNull(response);
        assertNull(response.date());
        assertEquals(0.0, response.amount());
        assertNull(response.type());
        assertNull(response.approvalCode());
    }

    @Test
    @DisplayName("Should create TransactionResponse with different transaction types")
    void shouldCreateTransactionResponseWithDifferentTransactionTypes() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = 200.0;
        String[] types = {"DEPOSIT", "WITHDRAWAL", "CHECK_PAYMENT", "PHONE_BILL_PAYMENT"};

        // When & Then
        for (String type : types) {
            String approvalCode = "CODE_" + type;
            TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);
            
            assertNotNull(response);
            assertEquals(date, response.date());
            assertEquals(amount, response.amount());
            assertEquals(type, response.type());
            assertEquals(approvalCode, response.approvalCode());
        }
    }

    @Test
    @DisplayName("Should create TransactionResponse with negative amount")
    void shouldCreateTransactionResponseWithNegativeAmount() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = -50.25;
        String type = "WITHDRAWAL";
        String approvalCode = "NEG123";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(date, response.date());
        assertEquals(amount, response.amount());
        assertEquals(type, response.type());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should create TransactionResponse with zero amount")
    void shouldCreateTransactionResponseWithZeroAmount() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = 0.0;
        String type = "DEPOSIT";
        String approvalCode = "ZERO123";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(date, response.date());
        assertEquals(amount, response.amount());
        assertEquals(type, response.type());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should create TransactionResponse with large amount")
    void shouldCreateTransactionResponseWithLargeAmount() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = 999999.99;
        String type = "DEPOSIT";
        String approvalCode = "LARGE123";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(date, response.date());
        assertEquals(amount, response.amount());
        assertEquals(type, response.type());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should have correct record behavior")
    void shouldHaveCorrectRecordBehavior() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = 100.0;
        String type = "DEPOSIT";
        String approvalCode = "ABC123";

        TransactionResponse response1 = new TransactionResponse(date, amount, type, approvalCode);
        TransactionResponse response2 = new TransactionResponse(date, amount, type, approvalCode);
        TransactionResponse response3 = new TransactionResponse(date, 200.0, type, approvalCode);

        // When & Then
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
        
        String expectedToString = "TransactionResponse[date=" + date + 
                                ", amount=" + amount + 
                                ", type=" + type + 
                                ", approvalCode=" + approvalCode + "]";
        assertEquals(expectedToString, response1.toString());
    }

    @Test
    @DisplayName("Should handle special characters in approval code")
    void shouldHandleSpecialCharactersInApprovalCode() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = 100.0;
        String type = "DEPOSIT";
        String approvalCode = "ABC-123_XYZ@456";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = 100.0;
        String type = "";
        String approvalCode = "";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(type, response.type());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle maximum double value")
    void shouldHandleMaximumDoubleValue() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = Double.MAX_VALUE;
        String type = "DEPOSIT";
        String approvalCode = "MAX123";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(amount, response.amount());
    }

    @Test
    @DisplayName("Should handle minimum double value")
    void shouldHandleMinimumDoubleValue() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = Double.MIN_VALUE;
        String type = "DEPOSIT";
        String approvalCode = "MIN123";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(amount, response.amount());
    }

    @Test
    @DisplayName("Should handle NaN value")
    void shouldHandleNaNValue() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double amount = Double.NaN;
        String type = "DEPOSIT";
        String approvalCode = "NAN123";

        // When
        TransactionResponse response = new TransactionResponse(date, amount, type, approvalCode);

        // Then
        assertNotNull(response);
        assertTrue(Double.isNaN(response.amount()));
    }

    @Test
    @DisplayName("Should handle infinity values")
    void shouldHandleInfinityValues() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        double positiveInfinity = Double.POSITIVE_INFINITY;
        double negativeInfinity = Double.NEGATIVE_INFINITY;
        String type = "DEPOSIT";
        String approvalCode = "INF123";

        // When
        TransactionResponse positiveResponse = new TransactionResponse(date, positiveInfinity, type, approvalCode);
        TransactionResponse negativeResponse = new TransactionResponse(date, negativeInfinity, type, approvalCode);

        // Then
        assertNotNull(positiveResponse);
        assertNotNull(negativeResponse);
        assertEquals(positiveInfinity, positiveResponse.amount());
        assertEquals(negativeInfinity, negativeResponse.amount());
    }
} 