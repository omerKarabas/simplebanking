package com.eteration.simplebanking.model.dto.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CreateAccountRequest Tests")
class CreateAccountRequestTest {

    @Test
    @DisplayName("Should create CreateAccountRequest with valid data")
    void shouldCreateCreateAccountRequestWithValidData() {
        // Given
        String owner = "John Doe";
        String accountNumber = "1234567890";

        // When
        CreateAccountRequest request = new CreateAccountRequest(owner, accountNumber);

        // Then
        assertNotNull(request);
        assertEquals(owner, request.owner());
        assertEquals(accountNumber, request.accountNumber());
    }

    @Test
    @DisplayName("Should create CreateAccountRequest with null values")
    void shouldCreateCreateAccountRequestWithNullValues() {
        // Given & When
        CreateAccountRequest request = new CreateAccountRequest(null, null);

        // Then
        assertNotNull(request);
        assertNull(request.owner());
        assertNull(request.accountNumber());
    }

    @Test
    @DisplayName("Should create CreateAccountRequest with empty strings")
    void shouldCreateCreateAccountRequestWithEmptyStrings() {
        // Given
        String owner = "";
        String accountNumber = "";

        // When
        CreateAccountRequest request = new CreateAccountRequest(owner, accountNumber);

        // Then
        assertNotNull(request);
        assertEquals(owner, request.owner());
        assertEquals(accountNumber, request.accountNumber());
    }

    @Test
    @DisplayName("Should have correct record behavior")
    void shouldHaveCorrectRecordBehavior() {
        // Given
        String owner = "Jane Smith";
        String accountNumber = "9876543210";
        CreateAccountRequest request1 = new CreateAccountRequest(owner, accountNumber);
        CreateAccountRequest request2 = new CreateAccountRequest(owner, accountNumber);
        CreateAccountRequest request3 = new CreateAccountRequest("Different Owner", accountNumber);

        // When & Then
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
        
        String expectedToString = "CreateAccountRequest[owner=" + owner + ", accountNumber=" + accountNumber + "]";
        assertEquals(expectedToString, request1.toString());
    }

    @Test
    @DisplayName("Should handle special characters in owner name")
    void shouldHandleSpecialCharactersInOwnerName() {
        // Given
        String owner = "José María García-López";
        String accountNumber = "12345";

        // When
        CreateAccountRequest request = new CreateAccountRequest(owner, accountNumber);

        // Then
        assertEquals(owner, request.owner());
        assertEquals(accountNumber, request.accountNumber());
    }

    @Test
    @DisplayName("Should handle long account numbers")
    void shouldHandleLongAccountNumbers() {
        // Given
        String owner = "Test Owner";
        String accountNumber = "12345678901234567890";

        // When
        CreateAccountRequest request = new CreateAccountRequest(owner, accountNumber);

        // Then
        assertEquals(owner, request.owner());
        assertEquals(accountNumber, request.accountNumber());
    }
} 