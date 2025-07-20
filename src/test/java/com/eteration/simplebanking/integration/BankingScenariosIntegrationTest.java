package com.eteration.simplebanking.integration;

import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.TransactionRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class BankingScenariosIntegrationTest {

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
    void shouldCompleteFullBankingScenarioSuccessfully() throws Exception {
        // Given - Complete banking scenario
        String accountNumber = "E2E_ACC_001";
        String ownerName = "End-to-End Test User";

        // Step 1: Create Account
        CreateAccountRequest createRequest = new CreateAccountRequest(ownerName, accountNumber);
        
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(jsonPath("$.owner").value(ownerName))
                .andExpect(jsonPath("$.balance").value(0.0));

        // Step 2: Initial Deposit
        TransactionRequest initialDeposit = new TransactionRequest(5000.0);
        
        mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(initialDeposit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.approvalCode").exists());

        // Step 3: Multiple Withdrawals
        TransactionRequest withdrawal1 = new TransactionRequest(1000.0);
        TransactionRequest withdrawal2 = new TransactionRequest(500.0);
        TransactionRequest withdrawal3 = new TransactionRequest(750.0);

        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawal1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawal2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(withdrawal3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // Step 4: Additional Deposit
        TransactionRequest additionalDeposit = new TransactionRequest(2000.0);
        
        mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(additionalDeposit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // Step 5: Large Withdrawal (should succeed)
        TransactionRequest largeWithdrawal = new TransactionRequest(3000.0);
        
        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(largeWithdrawal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // Step 6: Attempt Excessive Withdrawal (should fail)
        TransactionRequest excessiveWithdrawal = new TransactionRequest(5000.0);
        
        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(excessiveWithdrawal)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldHandleMultiAccountBankingScenarioSuccessfully() throws Exception {
        // Given - Multiple accounts scenario
        String[] accountNumbers = {"MULTI_001", "MULTI_002", "MULTI_003"};
        String[] owners = {"User One", "User Two", "User Three"};

        // Step 1: Create Multiple Accounts
        for (int i = 0; i < accountNumbers.length; i++) {
            CreateAccountRequest createRequest = new CreateAccountRequest(owners[i], accountNumbers[i]);
            
            mockMvc.perform(post("/bank-account/v1/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accountNumber").value(accountNumbers[i]))
                    .andExpect(jsonPath("$.owner").value(owners[i]));
        }

        // Step 2: Perform Transactions on Each Account
        for (String accountNumber : accountNumbers) {
            // Initial deposit
            TransactionRequest deposit = new TransactionRequest(1000.0);
            mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(deposit)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("OK"));

            // Withdrawal
            TransactionRequest withdrawal = new TransactionRequest(300.0);
            mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(withdrawal)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("OK"));
        }
    }

    @Test
    void shouldHandleStressTestWithMultipleTransactionsSuccessfully() throws Exception {
        // Given - Stress test scenario
        String accountNumber = "STRESS_TEST";
        CreateAccountRequest createRequest = new CreateAccountRequest("Stress Test User", accountNumber);

        // Step 1: Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // Step 2: Large Initial Deposit
        TransactionRequest largeDeposit = new TransactionRequest(10000.0);
        mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(largeDeposit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // Step 3: Multiple Small Transactions
        for (int i = 1; i <= 10; i++) {
            TransactionRequest smallDeposit = new TransactionRequest(100.0);
            mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(smallDeposit)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("OK"));

            TransactionRequest smallWithdrawal = new TransactionRequest(50.0);
            mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(smallWithdrawal)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("OK"));
        }

        // Step 4: Large Withdrawal
        TransactionRequest largeWithdrawal = new TransactionRequest(5000.0);
        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(largeWithdrawal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    void shouldHandleErrorHandlingScenarioSuccessfully() throws Exception {
        // Given - Error handling test scenario
        String accountNumber = "ERROR_TEST";

        // Step 1: Try to debit non-existent account
        TransactionRequest debitRequest = new TransactionRequest(100.0);
        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest)))
                .andExpect(status().is4xxClientError());

        // Step 2: Create account
        CreateAccountRequest createRequest = new CreateAccountRequest("Error Test User", accountNumber);
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // Step 3: Try to debit with insufficient balance
        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(debitRequest)))
                .andExpect(status().is5xxServerError());

        // Step 4: Try to credit with negative amount
        TransactionRequest negativeCredit = new TransactionRequest(-100.0);
        mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(negativeCredit)))
                .andExpect(status().is5xxServerError());

        // Step 5: Try to debit with zero amount
        TransactionRequest zeroDebit = new TransactionRequest(0.0);
        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(zeroDebit)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldHandleBoundaryValueTestSuccessfully() throws Exception {
        // Given - Boundary value test scenario
        String accountNumber = "BOUNDARY_TEST";
        CreateAccountRequest createRequest = new CreateAccountRequest("Boundary Test User", accountNumber);

        // Step 1: Create Account
        mockMvc.perform(post("/bank-account/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk());

        // Step 2: Test minimum positive amount
        TransactionRequest minAmount = new TransactionRequest(0.01);
        mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(minAmount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // Step 3: Test maximum reasonable amount
        TransactionRequest maxAmount = new TransactionRequest(999999.99);
        mockMvc.perform(post("/bank-account/v1/credit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(maxAmount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));

        // Step 4: Test withdrawal of exact balance
        TransactionRequest exactWithdrawal = new TransactionRequest(1000000.0);
        mockMvc.perform(post("/bank-account/v1/debit/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exactWithdrawal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));
    }
} 