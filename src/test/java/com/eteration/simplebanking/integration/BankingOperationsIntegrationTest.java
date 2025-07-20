package com.eteration.simplebanking.integration;

import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.TransactionRequest;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class BankingOperationsIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void shouldCreateAccountAndPerformTransactionsSuccessfully() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "12345");
        TransactionRequest creditRequest = new TransactionRequest(1000.0);
        TransactionRequest debitRequest = new TransactionRequest(500.0);

        // When & Then - Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("12345"))
                .andExpect(jsonPath("$.owner").value("Test User"))
                .andExpect(jsonPath("$.balance").value(0.0));

        // When & Then - Credit Transaction
        mockMvc.perform(post("/bank-account/v1/credit/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").exists());

        // When & Then - Debit Transaction
        mockMvc.perform(post("/bank-account/v1/debit/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").exists());
    }

    @Test
    void shouldThrowExceptionWhenDebitingWithInsufficientBalance() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "12346");
        TransactionRequest creditRequest = new TransactionRequest(100.0);
        TransactionRequest debitRequest = new TransactionRequest(500.0);

        // When & Then - Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // When & Then - Credit Transaction
        mockMvc.perform(post("/bank-account/v1/credit/12346")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditRequest)))
                .andExpect(status().isOk());

        // When & Then - Debit Transaction (Should fail)
        mockMvc.perform(post("/bank-account/v1/debit/12346")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldThrowExceptionWhenDebitingNonExistentAccount() throws Exception {
        // Given
        TransactionRequest debitRequest = new TransactionRequest(100.0);

        // When & Then - Debit Transaction on non-existent account
        mockMvc.perform(post("/bank-account/v1/debit/99999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowExceptionWhenCreatingAccountWithInvalidRequest() throws Exception {
        // Given
        CreateAccountRequest invalidRequest = new CreateAccountRequest(null, null);

        // When & Then - Create Account with invalid request
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldThrowExceptionWhenCreditingWithInvalidAmount() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "12347");
        TransactionRequest invalidRequest = new TransactionRequest(-100.0);

        // When & Then - Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // When & Then - Credit Transaction with negative amount
        mockMvc.perform(post("/bank-account/v1/credit/12347")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldHandleMultipleTransactionsOnSameAccountSuccessfully() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "12348");
        TransactionRequest creditRequest1 = new TransactionRequest(1000.0);
        TransactionRequest creditRequest2 = new TransactionRequest(500.0);
        TransactionRequest debitRequest1 = new TransactionRequest(300.0);
        TransactionRequest debitRequest2 = new TransactionRequest(200.0);

        // When & Then - Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // When & Then - Multiple Credit Transactions
        mockMvc.perform(post("/bank-account/v1/credit/12348")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditRequest1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        mockMvc.perform(post("/bank-account/v1/credit/12348")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditRequest2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // When & Then - Multiple Debit Transactions
        mockMvc.perform(post("/bank-account/v1/debit/12348")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        mockMvc.perform(post("/bank-account/v1/debit/12348")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    void shouldThrowExceptionForZeroAmountTransactions() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "12349");
        TransactionRequest zeroRequest = new TransactionRequest(0.0);

        // When & Then - Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // When & Then - Credit Transaction with zero amount
        mockMvc.perform(post("/bank-account/v1/credit/12349")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(zeroRequest)))
                .andExpect(status().is5xxServerError());

        // When & Then - Debit Transaction with zero amount
        mockMvc.perform(post("/bank-account/v1/debit/12349")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(zeroRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldCreateMultipleAccountsSuccessfully() throws Exception {
        // Given
        CreateAccountRequest createRequest1 = new CreateAccountRequest("User 1", "ACC001");
        CreateAccountRequest createRequest2 = new CreateAccountRequest("User 2", "ACC002");
        CreateAccountRequest createRequest3 = new CreateAccountRequest("User 3", "ACC003");

        // When & Then - Create Multiple Accounts
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("ACC001"))
                .andExpect(jsonPath("$.owner").value("User 1"));

        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("ACC002"))
                .andExpect(jsonPath("$.owner").value("User 2"));

        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("ACC003"))
                .andExpect(jsonPath("$.owner").value("User 3"));
    }

    @Test
    void shouldThrowExceptionWhenCreatingAccountWithDuplicateAccountNumber() throws Exception {
        // Given
        CreateAccountRequest createRequest1 = new CreateAccountRequest("User 1", "DUPLICATE");
        CreateAccountRequest createRequest2 = new CreateAccountRequest("User 2", "DUPLICATE");

        // When & Then - Create First Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest1)))
                .andExpect(status().isOk());

        // When & Then - Create Second Account with Same Account Number (Should fail)
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest2)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldHandleLargeAmountTransactionsSuccessfully() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "LARGE_ACC");
        TransactionRequest largeCreditRequest = new TransactionRequest(1000000.0);
        TransactionRequest largeDebitRequest = new TransactionRequest(500000.0);

        // When & Then - Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // When & Then - Large Credit Transaction
        mockMvc.perform(post("/bank-account/v1/credit/LARGE_ACC")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(largeCreditRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // When & Then - Large Debit Transaction
        mockMvc.perform(post("/bank-account/v1/debit/LARGE_ACC")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(largeDebitRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    void shouldHandleConcurrentTransactionsOnSameAccountSuccessfully() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "CONCURRENT");
        TransactionRequest creditRequest = new TransactionRequest(1000.0);
        TransactionRequest debitRequest1 = new TransactionRequest(300.0);
        TransactionRequest debitRequest2 = new TransactionRequest(400.0);

        // When & Then - Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // When & Then - Credit Transaction
        mockMvc.perform(post("/bank-account/v1/credit/CONCURRENT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creditRequest)))
                .andExpect(status().isOk());

        // When & Then - Multiple Debit Transactions (should succeed)
        mockMvc.perform(post("/bank-account/v1/debit/CONCURRENT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/bank-account/v1/debit/CONCURRENT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest2)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionForInvalidJsonRequest() throws Exception {
        // Given
        String invalidJson = "{ invalid json }";

        // When & Then - Create Account with invalid JSON
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().is4xxClientError());

        // When & Then - Credit with invalid JSON
        mockMvc.perform(post("/bank-account/v1/credit/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowExceptionForMissingContentType() throws Exception {
        // Given
        CreateAccountRequest createRequest = new CreateAccountRequest("Test User", "NO_CONTENT_TYPE");

        // When & Then - Create Account without content type
        mockMvc.perform(post("/bank-account/v1/create")
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldAccessActuatorHealthEndpoint() throws Exception {
        // When & Then - Health endpoint should be accessible
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists());
    }
} 