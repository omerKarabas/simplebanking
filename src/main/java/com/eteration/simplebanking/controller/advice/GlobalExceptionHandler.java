package com.eteration.simplebanking.controller.advice;

import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.eteration.simplebanking.exception.StrategyNotFoundException;
import com.eteration.simplebanking.exception.TransactionValidationException;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import com.eteration.simplebanking.model.dto.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;


    private String getMessage(MessageKeys messageKey) {
        return messageSource.getMessage(messageKey.getKey(), null, LocaleContextHolder.getLocale());
    }


    private String getMessage(MessageKeys messageKey, Object... args) {
        return messageSource.getMessage(messageKey.getKey(), args, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex,
                                                                            WebRequest request) {

        log.error("Insufficient balance exception: {}", ex.getMessage());

        // If exception has message key, use it directly; otherwise use default message
        String i18nMessage = ex.hasMessageKey() 
            ? getMessage(ex.getMessageKey())
            : getMessage(MessageKeys.ERROR_INSUFFICIENT_BALANCE);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_INSUFFICIENT_BALANCE))
                .message(i18nMessage)
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(
            AccountNotFoundException ex, WebRequest request) {

        log.error("Account not found exception: {}", ex.getMessage());

        // If exception has message key, use it directly; otherwise use default message with parameters
        String i18nMessage = ex.hasMessageKey() 
            ? getMessage(ex.getMessageKey(), ex.getParameters())
            : getMessage(MessageKeys.ERROR_ACCOUNT_NOT_FOUND, ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_ACCOUNT_NOT_FOUND))
                .message(i18nMessage)
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransactionException(InvalidTransactionException ex,
                                                                           WebRequest request) {

        log.error("Invalid transaction exception: {}", ex.getMessage());

        String i18nMessage = ex.hasMessageKey()
            ? getMessage(ex.getMessageKey())
            : getMessage(MessageKeys.ERROR_INVALID_TRANSACTION, ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_INVALID_TRANSACTION))
                .message(i18nMessage)
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(StrategyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStrategyNotFoundException(StrategyNotFoundException ex,
                                                                           WebRequest request) {

        log.error("Strategy not found exception: {}", ex.getMessage());

        // If exception has message key, use it directly; otherwise use default message with parameters
        String i18nMessage = ex.hasMessageKey() 
            ? getMessage(ex.getMessageKey(), ex.getParameters())
            : getMessage(MessageKeys.STRATEGY_NOT_FOUND, ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_INVALID_TRANSACTION))
                .message(i18nMessage)
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(TransactionValidationException.class)
    public ResponseEntity<ErrorResponse> handleTransactionValidationException(TransactionValidationException ex,
                                                                           WebRequest request) {

        log.error("Transaction validation exception: {}", ex.getMessage());

        // If exception has message key, use it directly; otherwise fallback to mapping
        String i18nMessage = ex.hasMessageKey() 
            ? getMessage(ex.getMessageKey())
            : mapValidationExceptionToI18n(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_VALIDATION_ERROR))
                .message(i18nMessage)
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Maps validation exception messages to i18n message keys.
     * This method is used as fallback when exception doesn't have message key.
     */
    private String mapValidationExceptionToI18n(String exceptionMessage) {
        if (exceptionMessage == null) {
            return getMessage(MessageKeys.ERROR_VALIDATION_FAILED);
        }
        
        switch (exceptionMessage) {
            case "Transaction type cannot be null":
                return getMessage(MessageKeys.VALIDATION_TRANSACTION_TYPE_NULL);
            case "Bank account cannot be null":
                return getMessage(MessageKeys.VALIDATION_BANK_ACCOUNT_NULL);
            case "Account number cannot be null or empty":
                return getMessage(MessageKeys.VALIDATION_ACCOUNT_NUMBER_NULL_OR_EMPTY);
            case "Transaction parameters cannot be null":
                return getMessage(MessageKeys.VALIDATION_TRANSACTION_PARAMETERS_NULL);
            case "Strategy returned null transaction":
                return getMessage(MessageKeys.VALIDATION_TRANSACTION_NULL);
            case "Transaction amount cannot be negative":
                return getMessage(MessageKeys.VALIDATION_TRANSACTION_AMOUNT_NEGATIVE);
            case "Transaction date cannot be null":
                return getMessage(MessageKeys.VALIDATION_TRANSACTION_DATE_NULL);
            default:
                return getMessage(MessageKeys.ERROR_VALIDATION_FAILED);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        log.error("Illegal argument exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Invalid Argument")
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        log.error("Validation exception: {}", ex.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_VALIDATION_ERROR))
                .message(getMessage(MessageKeys.ERROR_VALIDATION_FAILED))
                .details(fieldErrors)
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

        @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                                WebRequest request) {

        log.error("HTTP message not readable exception: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_INVALID_REQUEST_BODY))
                .message(getMessage(MessageKeys.ERROR_INVALID_REQUEST_BODY))
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {

        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        log.error("Exception stack trace:", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(getMessage(MessageKeys.ERROR_TITLE_INTERNAL_SERVER_ERROR))
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
} 