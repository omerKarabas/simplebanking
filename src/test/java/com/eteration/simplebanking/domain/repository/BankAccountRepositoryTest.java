package com.eteration.simplebanking.domain.repository;

import com.eteration.simplebanking.domain.entity.BankAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BankAccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void testRepositoryInjection() {
        // Then
        assertNotNull(bankAccountRepository);
        assertNotNull(entityManager);
    }

    @Test
    void save_NewAccount_Success() {
        // Given
        BankAccount account = BankAccount.builder()
                .owner("New Owner")
                .accountNumber("54321")
                .balance(500.0)
                .build();

        // When
        BankAccount savedAccount = bankAccountRepository.save(account);

        // Then
        assertNotNull(savedAccount);
        assertNotNull(savedAccount.getId());
        assertEquals("54321", savedAccount.getAccountNumber());
        assertEquals("New Owner", savedAccount.getOwner());
        assertEquals(500.0, savedAccount.getBalance(), 0.001);
    }

    @Test
    void findByAccountNumber_ExistingAccount_ReturnsAccount() {
        // Given
        BankAccount account = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();
        
        entityManager.persistAndFlush(account);

        // When
        Optional<BankAccount> result = bankAccountRepository.findByAccountNumber("12345");

        // Then
        assertTrue(result.isPresent());
        assertEquals("12345", result.get().getAccountNumber());
        assertEquals("Test Owner", result.get().getOwner());
        assertEquals(1000.0, result.get().getBalance(), 0.001);
    }

    @Test
    void findByAccountNumber_NonExistentAccount_ReturnsEmpty() {
        // When
        Optional<BankAccount> result = bankAccountRepository.findByAccountNumber("99999");

        // Then
        assertFalse(result.isPresent());
    }
} 