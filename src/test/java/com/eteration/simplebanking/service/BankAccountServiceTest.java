package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.repository.BankAccountRepository;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.mapper.BankAccountMapper;
import com.eteration.simplebanking.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountMapper bankAccountMapper;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount testAccount;
    private BankAccountResponse testAccountResponse;

    @BeforeEach
    void setUp() {
        testAccount = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();

        testAccountResponse = new BankAccountResponse(
                "12345",
                "Test Owner",
                1000.0,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void createAccount_Success() {
        // Given
        String owner = "Test Owner";
        String accountNumber = "12345";
        
        BankAccount savedAccount = BankAccount.builder()
                .owner(owner)
                .accountNumber(accountNumber)
                .balance(0.0)
                .build();
        
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(savedAccount);
        when(bankAccountMapper.toAccountResponse(savedAccount)).thenReturn(testAccountResponse);

        // When
        BankAccountResponse result = bankAccountService.createAccount(owner, accountNumber);

        // Then
        assertNotNull(result);
        assertEquals(testAccountResponse, result);
        verify(bankAccountRepository).save(any(BankAccount.class));
        verify(bankAccountMapper).toAccountResponse(savedAccount);
    }

    @Test
    void findAccountByNumber_Success() {
        // Given
        String accountNumber = "12345";
        when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(testAccount));

        // When
        BankAccount result = bankAccountService.findAccountByNumber(accountNumber);

        // Then
        assertNotNull(result);
        assertEquals(testAccount, result);
        verify(bankAccountRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void findAccountByNumber_AccountNotFound_ThrowsException() {
        // Given
        String accountNumber = "99999";
        when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // When & Then
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            bankAccountService.findAccountByNumber(accountNumber);
        });
        
        assertEquals("Account not found: " + accountNumber, exception.getMessage());
        verify(bankAccountRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void getAccount_Success() {
        // Given
        String accountNumber = "12345";
        when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(testAccount));
        when(bankAccountMapper.toAccountResponse(testAccount)).thenReturn(testAccountResponse);

        // When
        BankAccountResponse result = bankAccountService.getAccount(accountNumber);

        // Then
        assertNotNull(result);
        assertEquals(testAccountResponse, result);
        verify(bankAccountRepository).findByAccountNumber(accountNumber);
        verify(bankAccountMapper).toAccountResponse(testAccount);
    }

    @Test
    void getAccount_AccountNotFound_ThrowsException() {
        // Given
        String accountNumber = "99999";
        when(bankAccountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // When & Then
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            bankAccountService.getAccount(accountNumber);
        });
        
        assertEquals("Account not found: " + accountNumber, exception.getMessage());
        verify(bankAccountRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void saveAccount_Success() {
        // Given
        when(bankAccountRepository.save(testAccount)).thenReturn(testAccount);

        // When
        bankAccountService.saveAccount(testAccount);

        // Then
        verify(bankAccountRepository).save(testAccount);
    }
} 