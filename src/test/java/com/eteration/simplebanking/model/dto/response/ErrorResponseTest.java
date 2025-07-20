package com.eteration.simplebanking.model.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

    @Test
    @DisplayName("Should create ErrorResponse with valid data")
    void shouldCreateErrorResponseWithValidData() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "Bad Request";
        String message = "Invalid input provided";
        String path = "/api/accounts";
        Map<String, String> details = new HashMap<>();
        details.put("field", "accountNumber");
        details.put("reason", "must not be null");

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertEquals(details, response.getDetails());
    }

    @Test
    @DisplayName("Should create ErrorResponse with null values")
    void shouldCreateErrorResponseWithNullValues() {
        // Given & When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(null)
                .status(500)
                .error(null)
                .message(null)
                .path(null)
                .details(null)
                .build();

        // Then
        assertNotNull(response);
        assertNull(response.getTimestamp());
        assertEquals(500, response.getStatus());
        assertNull(response.getError());
        assertNull(response.getMessage());
        assertNull(response.getPath());
        assertNull(response.getDetails());
    }

    @Test
    @DisplayName("Should create ErrorResponse with empty details map")
    void shouldCreateErrorResponseWithEmptyDetailsMap() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 404;
        String error = "Not Found";
        String message = "Account not found";
        String path = "/api/accounts/12345";
        Map<String, String> details = new HashMap<>();

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertNotNull(response.getDetails());
        assertTrue(response.getDetails().isEmpty());
    }

    @Test
    @DisplayName("Should create ErrorResponse with different HTTP status codes")
    void shouldCreateErrorResponseWithDifferentHttpStatusCodes() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        String error = "Test Error";
        String message = "Test Message";
        String path = "/test";
        Map<String, String> details = new HashMap<>();

        int[] statusCodes = {400, 401, 403, 404, 500, 502, 503};

        // When & Then
        for (int status : statusCodes) {
            ErrorResponse response = ErrorResponse.builder()
                    .timestamp(timestamp)
                    .status(status)
                    .error(error)
                    .message(message)
                    .path(path)
                    .details(details)
                    .build();

            assertNotNull(response);
            assertEquals(status, response.getStatus());
        }
    }

    @Test
    @DisplayName("Should create ErrorResponse with special characters in message")
    void shouldCreateErrorResponseWithSpecialCharactersInMessage() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "Bad Request";
        String message = "Invalid input: José María García-López";
        String path = "/api/accounts";
        Map<String, String> details = new HashMap<>();

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(message, response.getMessage());
    }

    @Test
    @DisplayName("Should create ErrorResponse with complex details map")
    void shouldCreateErrorResponseWithComplexDetailsMap() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 422;
        String error = "Unprocessable Entity";
        String message = "Validation failed";
        String path = "/api/transactions";
        Map<String, String> details = new HashMap<>();
        details.put("accountNumber", "must not be null");
        details.put("amount", "must be greater than 0");
        details.put("owner", "must not be empty");
        details.put("balance", "insufficient funds");

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(status, response.getStatus());
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
        assertEquals(details, response.getDetails());
        assertEquals(4, response.getDetails().size());
        assertEquals("must not be null", response.getDetails().get("accountNumber"));
        assertEquals("must be greater than 0", response.getDetails().get("amount"));
        assertEquals("must not be empty", response.getDetails().get("owner"));
        assertEquals("insufficient funds", response.getDetails().get("balance"));
    }

    @Test
    @DisplayName("Should have correct equals and hashCode behavior")
    void shouldHaveCorrectEqualsAndHashCodeBehavior() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "Bad Request";
        String message = "Invalid input";
        String path = "/api/test";
        Map<String, String> details = new HashMap<>();
        details.put("field", "value");

        ErrorResponse response1 = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        ErrorResponse response2 = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        ErrorResponse response3 = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(500)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // When & Then
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    @DisplayName("Should handle zero status code")
    void shouldHandleZeroStatusCode() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 0;
        String error = "Unknown Error";
        String message = "Unknown error occurred";
        String path = "/api/test";
        Map<String, String> details = new HashMap<>();

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(status, response.getStatus());
    }

    @Test
    @DisplayName("Should handle negative status code")
    void shouldHandleNegativeStatusCode() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = -1;
        String error = "Negative Error";
        String message = "Negative status code";
        String path = "/api/test";
        Map<String, String> details = new HashMap<>();

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(status, response.getStatus());
    }

    @Test
    @DisplayName("Should handle very long path")
    void shouldHandleVeryLongPath() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 404;
        String error = "Not Found";
        String message = "Resource not found";
        String path = "/api/v1/accounts/12345/transactions/67890/history/2024/01/15/deposits/withdrawals/checks/phone-bills";
        Map<String, String> details = new HashMap<>();

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(path, response.getPath());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 400;
        String error = "";
        String message = "";
        String path = "";
        Map<String, String> details = new HashMap<>();

        // When
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .details(details)
                .build();

        // Then
        assertNotNull(response);
        assertEquals(error, response.getError());
        assertEquals(message, response.getMessage());
        assertEquals(path, response.getPath());
    }
} 