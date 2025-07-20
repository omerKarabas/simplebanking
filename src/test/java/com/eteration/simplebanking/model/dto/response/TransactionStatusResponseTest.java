package com.eteration.simplebanking.model.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TransactionStatusResponse Tests")
class TransactionStatusResponseTest {

    @Test
    @DisplayName("Should create TransactionStatusResponse with valid data")
    void shouldCreateTransactionStatusResponseWithValidData() {
        // Given
        String status = "SUCCESS";
        String approvalCode = "ABC123";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should create TransactionStatusResponse with null values")
    void shouldCreateTransactionStatusResponseWithNullValues() {
        // Given & When
        TransactionStatusResponse response = new TransactionStatusResponse(null, null);

        // Then
        assertNotNull(response);
        assertNull(response.status());
        assertNull(response.approvalCode());
    }

    @Test
    @DisplayName("Should create TransactionStatusResponse with different status values")
    void shouldCreateTransactionStatusResponseWithDifferentStatusValues() {
        // Given
        String approvalCode = "XYZ789";
        String[] statuses = {"SUCCESS", "FAILED", "PENDING", "CANCELLED", "PROCESSING"};

        // When & Then
        for (String status : statuses) {
            TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);
            
            assertNotNull(response);
            assertEquals(status, response.status());
            assertEquals(approvalCode, response.approvalCode());
        }
    }

    @Test
    @DisplayName("Should create TransactionStatusResponse with empty strings")
    void shouldCreateTransactionStatusResponseWithEmptyStrings() {
        // Given
        String status = "";
        String approvalCode = "";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should have correct record behavior")
    void shouldHaveCorrectRecordBehavior() {
        // Given
        String status = "SUCCESS";
        String approvalCode = "ABC123";

        TransactionStatusResponse response1 = new TransactionStatusResponse(status, approvalCode);
        TransactionStatusResponse response2 = new TransactionStatusResponse(status, approvalCode);
        TransactionStatusResponse response3 = new TransactionStatusResponse("FAILED", approvalCode);

        // When & Then
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
        
        String expectedToString = "TransactionStatusResponse[status=" + status + ", approvalCode=" + approvalCode + "]";
        assertEquals(expectedToString, response1.toString());
    }

    @Test
    @DisplayName("Should handle special characters in approval code")
    void shouldHandleSpecialCharactersInApprovalCode() {
        // Given
        String status = "SUCCESS";
        String approvalCode = "ABC-123_XYZ@456";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle long approval codes")
    void shouldHandleLongApprovalCodes() {
        // Given
        String status = "SUCCESS";
        String approvalCode = "VERY_LONG_APPROVAL_CODE_123456789012345678901234567890";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle numeric approval codes")
    void shouldHandleNumericApprovalCodes() {
        // Given
        String status = "SUCCESS";
        String approvalCode = "123456789";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle mixed case status")
    void shouldHandleMixedCaseStatus() {
        // Given
        String status = "SuCcEsS";
        String approvalCode = "ABC123";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle status with spaces")
    void shouldHandleStatusWithSpaces() {
        // Given
        String status = "SUCCESS WITH SPACES";
        String approvalCode = "ABC123";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle status with special characters")
    void shouldHandleStatusWithSpecialCharacters() {
        // Given
        String status = "SUCCESS-STATUS_123";
        String approvalCode = "ABC123";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle very long status")
    void shouldHandleVeryLongStatus() {
        // Given
        String status = "VERY_LONG_STATUS_MESSAGE_THAT_GOES_ON_AND_ON_AND_ON_FOR_A_VERY_LONG_TIME";
        String approvalCode = "ABC123";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle status with unicode characters")
    void shouldHandleStatusWithUnicodeCharacters() {
        // Given
        String status = "SUCCESS_状态_émojis_🎉";
        String approvalCode = "ABC123";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }

    @Test
    @DisplayName("Should handle approval code with unicode characters")
    void shouldHandleApprovalCodeWithUnicodeCharacters() {
        // Given
        String status = "SUCCESS";
        String approvalCode = "ABC123_状态_émojis_🎉";

        // When
        TransactionStatusResponse response = new TransactionStatusResponse(status, approvalCode);

        // Then
        assertNotNull(response);
        assertEquals(status, response.status());
        assertEquals(approvalCode, response.approvalCode());
    }
} 