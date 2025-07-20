package com.eteration.simplebanking.model.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BankAccountResponse Tests")
class BankAccountResponseTest {

    @Test
    @DisplayName("Should create BankAccountResponse with valid data")
    void shouldCreateBankAccountResponseWithValidData() {
        // Given
        String accountNumber = "1234567890";
        String owner = "John Doe";
        double balance = 1000.50;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(accountNumber, response.accountNumber());
        assertEquals(owner, response.owner());
        assertEquals(balance, response.balance());
        assertEquals(createDate, response.createDate());
        assertEquals(transactions, response.transactions());
    }

    @Test
    @DisplayName("Should create BankAccountResponse with null values")
    void shouldCreateBankAccountResponseWithNullValues() {
        // Given & When
        BankAccountResponse response = new BankAccountResponse(null, null, 0.0, null, null);

        // Then
        assertNotNull(response);
        assertNull(response.accountNumber());
        assertNull(response.owner());
        assertEquals(0.0, response.balance());
        assertNull(response.createDate());
        assertNull(response.transactions());
    }

    @Test
    @DisplayName("Should create BankAccountResponse with empty transactions list")
    void shouldCreateBankAccountResponseWithEmptyTransactionsList() {
        // Given
        String accountNumber = "12345";
        String owner = "Jane Smith";
        double balance = 500.0;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(accountNumber, response.accountNumber());
        assertEquals(owner, response.owner());
        assertEquals(balance, response.balance());
        assertEquals(createDate, response.createDate());
        assertNotNull(response.transactions());
        assertTrue(response.transactions().isEmpty());
    }

    @Test
    @DisplayName("Should create BankAccountResponse with transactions")
    void shouldCreateBankAccountResponseWithTransactions() {
        // Given
        String accountNumber = "12345";
        String owner = "Bob Johnson";
        double balance = 2000.0;
        LocalDateTime createDate = LocalDateTime.now();
        
        List<TransactionResponse> transactions = new ArrayList<>();
        transactions.add(new TransactionResponse(LocalDateTime.now(), 100.0, "DEPOSIT", "ABC123"));
        transactions.add(new TransactionResponse(LocalDateTime.now(), 50.0, "WITHDRAWAL", "DEF456"));

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(accountNumber, response.accountNumber());
        assertEquals(owner, response.owner());
        assertEquals(balance, response.balance());
        assertEquals(createDate, response.createDate());
        assertEquals(transactions, response.transactions());
        assertEquals(2, response.transactions().size());
    }

    @Test
    @DisplayName("Should have correct record behavior")
    void shouldHaveCorrectRecordBehavior() {
        // Given
        String accountNumber = "12345";
        String owner = "Test Owner";
        double balance = 1000.0;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        BankAccountResponse response1 = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);
        BankAccountResponse response2 = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);
        BankAccountResponse response3 = new BankAccountResponse("99999", owner, balance, createDate, transactions);

        // When & Then
        assertEquals(response1, response2);
        assertNotEquals(response1, response3);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
        
        String expectedToString = "BankAccountResponse[accountNumber=" + accountNumber + 
                                ", owner=" + owner + 
                                ", balance=" + balance + 
                                ", createDate=" + createDate + 
                                ", transactions=" + transactions + "]";
        assertEquals(expectedToString, response1.toString());
    }

    @Test
    @DisplayName("Should handle negative balance")
    void shouldHandleNegativeBalance() {
        // Given
        String accountNumber = "12345";
        String owner = "Test Owner";
        double balance = -500.0;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(balance, response.balance());
    }

    @Test
    @DisplayName("Should handle zero balance")
    void shouldHandleZeroBalance() {
        // Given
        String accountNumber = "12345";
        String owner = "Test Owner";
        double balance = 0.0;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(balance, response.balance());
    }

    @Test
    @DisplayName("Should handle large balance")
    void shouldHandleLargeBalance() {
        // Given
        String accountNumber = "12345";
        String owner = "Test Owner";
        double balance = 999999999.99;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(balance, response.balance());
    }

    @Test
    @DisplayName("Should handle special characters in owner name")
    void shouldHandleSpecialCharactersInOwnerName() {
        // Given
        String accountNumber = "12345";
        String owner = "José María García-López";
        double balance = 1000.0;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(owner, response.owner());
    }

    @Test
    @DisplayName("Should handle long account numbers")
    void shouldHandleLongAccountNumbers() {
        // Given
        String accountNumber = "12345678901234567890";
        String owner = "Test Owner";
        double balance = 1000.0;
        LocalDateTime createDate = LocalDateTime.now();
        List<TransactionResponse> transactions = new ArrayList<>();

        // When
        BankAccountResponse response = new BankAccountResponse(accountNumber, owner, balance, createDate, transactions);

        // Then
        assertNotNull(response);
        assertEquals(accountNumber, response.accountNumber());
    }
} 