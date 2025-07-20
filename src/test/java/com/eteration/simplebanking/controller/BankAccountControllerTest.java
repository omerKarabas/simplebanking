package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.TransactionRequest;
import com.eteration.simplebanking.service.interfaces.BankingFacadeService;

import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

import static com.eteration.simplebanking.TestConstants.*;
import static com.eteration.simplebanking.TestDataBuilder.*;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class BankAccountControllerTest {

    @InjectMocks
    private BankAccountController controller;
 
    @Mock
    private BankingFacadeService bankingFacadeService;

    @Test
    void givenId_Credit_thenReturnJson() {
        TransactionStatusResponse transactionResponse = createTransactionStatusResponse();
        doReturn(transactionResponse).when(bankingFacadeService).credit(TEST_ACCOUNT_NUMBER_2, CREDIT_AMOUNT);
        ResponseEntity<TransactionStatusResponse> result = controller.credit(TEST_ACCOUNT_NUMBER_2, createCreditAmountRequest());
        verify(bankingFacadeService, times(1)).credit(TEST_ACCOUNT_NUMBER_2, CREDIT_AMOUNT);
        assertEquals(SUCCESS_STATUS, Objects.requireNonNull(result.getBody()).status());
    }

    @Test
    void givenId_CreditAndThenDebit_thenReturnJson() {
        TransactionStatusResponse creditResponse = createCreditTransactionResponse();
        TransactionStatusResponse debitResponse = createDebitTransactionResponse();
        doReturn(creditResponse).when(bankingFacadeService).credit(TEST_ACCOUNT_NUMBER_2, CREDIT_AMOUNT);
        doReturn(debitResponse).when(bankingFacadeService).debit(TEST_ACCOUNT_NUMBER_2, DEBIT_AMOUNT);
        ResponseEntity<TransactionStatusResponse> result = controller.credit(TEST_ACCOUNT_NUMBER_2, createCreditAmountRequest());
        ResponseEntity<TransactionStatusResponse> result2 = controller.debit(TEST_ACCOUNT_NUMBER_2, createDebitAmountRequest());
        verify(bankingFacadeService, times(1)).credit(TEST_ACCOUNT_NUMBER_2, CREDIT_AMOUNT);
        verify(bankingFacadeService, times(1)).debit(TEST_ACCOUNT_NUMBER_2, DEBIT_AMOUNT);
        assertEquals(SUCCESS_STATUS, Objects.requireNonNull(result.getBody()).status());
        assertEquals(SUCCESS_STATUS, Objects.requireNonNull(result2.getBody()).status());
    }

    @Test
    void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() {
        TransactionStatusResponse creditResponse = createCreditTransactionResponse();
        doReturn(creditResponse).when(bankingFacadeService).credit(TEST_ACCOUNT_NUMBER_2, CREDIT_AMOUNT);
        doThrow(new RuntimeException(INSUFFICIENT_BALANCE_MESSAGE)).when(bankingFacadeService).debit(TEST_ACCOUNT_NUMBER_2, INSUFFICIENT_AMOUNT);
        ResponseEntity<TransactionStatusResponse> result = controller.credit(TEST_ACCOUNT_NUMBER_2, createCreditAmountRequest());
        assertEquals(SUCCESS_STATUS, Objects.requireNonNull(result.getBody()).status());
        verify(bankingFacadeService, times(1)).credit(TEST_ACCOUNT_NUMBER_2, CREDIT_AMOUNT);
        Assertions.assertThrows(RuntimeException.class, () -> {
            controller.debit(TEST_ACCOUNT_NUMBER_2, createInsufficientAmountRequest());
        });
    }

    @Test
    void givenId_CreateAccount_thenReturnJson() {
        BankAccountResponse accountResponse = createBankAccountResponse2();
        doReturn(accountResponse).when(bankingFacadeService).createBankAccount(TEST_OWNER_NAME_2, TEST_ACCOUNT_NUMBER_2);
        ResponseEntity<BankAccountResponse> result = controller.createBankAccount(createAccountRequest2());
        verify(bankingFacadeService, times(1)).createBankAccount(TEST_OWNER_NAME_2, TEST_ACCOUNT_NUMBER_2);
        assertEquals(accountResponse, result.getBody());
    }
} 