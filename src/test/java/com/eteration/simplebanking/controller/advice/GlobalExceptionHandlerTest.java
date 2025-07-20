package com.eteration.simplebanking.controller.advice;

import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import com.eteration.simplebanking.model.dto.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/test-endpoint");
        webRequest = new ServletWebRequest(request);
        
        LocaleContextHolder.setLocale(Locale.getDefault());
    }

    @Test
    void handleInsufficientBalanceException_Success() {
        InsufficientBalanceException exception = new InsufficientBalanceException("Insufficient balance");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_TITLE_INSUFFICIENT_BALANCE.getKey()), any(), any())).thenReturn("Insufficient Balance");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_INSUFFICIENT_BALANCE.getKey()), any(), any())).thenReturn("Insufficient balance for this transaction");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInsufficientBalanceException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Insufficient Balance", response.getBody().getError());
        assertEquals("Insufficient balance for this transaction", response.getBody().getMessage());
        assertEquals("uri=/test-endpoint", response.getBody().getPath());
    }

    @Test
    void handleAccountNotFoundException_Success() {
        AccountNotFoundException exception = new AccountNotFoundException("Account not found: 12345");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_TITLE_ACCOUNT_NOT_FOUND.getKey()), any(), any())).thenReturn("Account Not Found");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_ACCOUNT_NOT_FOUND.getKey()), any(), any())).thenReturn("Account not found with account number: Account not found: 12345");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleAccountNotFoundException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Account Not Found", response.getBody().getError());
        assertEquals("Account not found with account number: Account not found: 12345", response.getBody().getMessage());
        assertEquals("uri=/test-endpoint", response.getBody().getPath());
    }

    @Test
    void handleInvalidTransactionException_Success() {
        InvalidTransactionException exception = new InvalidTransactionException("Invalid transaction type");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_TITLE_INVALID_TRANSACTION.getKey()), any(), any())).thenReturn("Invalid Transaction");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_INVALID_TRANSACTION.getKey()), any(), any())).thenReturn("Invalid transaction: Invalid transaction type");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInvalidTransactionException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid Transaction", response.getBody().getError());
        assertEquals("Invalid transaction: Invalid transaction type", response.getBody().getMessage());
        assertEquals("uri=/test-endpoint", response.getBody().getPath());
    }

    @Test
    void handleIllegalArgumentException_Success() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument provided");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalArgumentException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid Argument", response.getBody().getError());
        assertEquals("Invalid argument provided", response.getBody().getMessage());
        assertEquals("uri=/test-endpoint", response.getBody().getPath());
    }

    @Test
    void handleGenericException_Success() {
        Exception exception = new Exception("Unexpected error occurred");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_TITLE_INTERNAL_SERVER_ERROR.getKey()), any(), any())).thenReturn("Internal Server Error");
        when(messageSource.getMessage(eq(MessageKeys.ERROR_INTERNAL_SERVER.getKey()), any(), any())).thenReturn("An unexpected error occurred");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Internal Server Error", response.getBody().getError());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
        assertEquals("uri=/test-endpoint", response.getBody().getPath());
    }

    @Test
    void handleInsufficientBalanceException_NullMessage() {
        InsufficientBalanceException exception = new InsufficientBalanceException();
        when(messageSource.getMessage(eq(MessageKeys.ERROR_TITLE_INSUFFICIENT_BALANCE.getKey()), any(), any())).thenReturn(null);
        when(messageSource.getMessage(eq(MessageKeys.ERROR_INSUFFICIENT_BALANCE.getKey()), any(), any())).thenReturn(null);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInsufficientBalanceException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertNull(response.getBody().getError());
        assertNull(response.getBody().getMessage());
    }

    @Test
    void handleAccountNotFoundException_NullMessage() {
        AccountNotFoundException exception = new AccountNotFoundException();
        when(messageSource.getMessage(eq(MessageKeys.ERROR_TITLE_ACCOUNT_NOT_FOUND.getKey()), any(), any())).thenReturn(null);
        when(messageSource.getMessage(eq(MessageKeys.ERROR_ACCOUNT_NOT_FOUND.getKey()), any(), any())).thenReturn(null);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleAccountNotFoundException(exception, webRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertNull(response.getBody().getError());
        assertNull(response.getBody().getMessage());
    }
} 