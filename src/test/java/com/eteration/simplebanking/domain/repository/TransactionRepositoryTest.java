package com.eteration.simplebanking.domain.repository;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.entity.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testRepositoryInjection() {
        // Then
        assertNotNull(transactionRepository);
        assertNotNull(entityManager);
    }

    @Test
    void save_DepositTransaction_Success() {
        // Given
        BankAccount account = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12345")
                .balance(1000.0)
                .build();
        
        entityManager.persistAndFlush(account);

        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(500.0);
        transaction.setDate(LocalDateTime.now());
        transaction.setApprovalCode("DEP-001");
        transaction.setAccount(account);

        // When
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Then
        assertNotNull(savedTransaction);
        assertNotNull(savedTransaction.getId());
        assertEquals(500.0, savedTransaction.getAmount(), 0.001);
        assertEquals("DEP-001", savedTransaction.getApprovalCode());
        assertEquals(account, savedTransaction.getAccount());
    }

    @Test
    void save_WithdrawalTransaction_Success() {
        // Given
        BankAccount account = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12346")
                .balance(1000.0)
                .build();
        
        entityManager.persistAndFlush(account);

        WithdrawalTransaction transaction = new WithdrawalTransaction();
        transaction.setAmount(200.0);
        transaction.setDate(LocalDateTime.now());
        transaction.setApprovalCode("WIT-001");
        transaction.setAccount(account);

        // When
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Then
        assertNotNull(savedTransaction);
        assertNotNull(savedTransaction.getId());
        assertEquals(200.0, savedTransaction.getAmount(), 0.001);
        assertEquals("WIT-001", savedTransaction.getApprovalCode());
        assertEquals(account, savedTransaction.getAccount());
    }

    @Test
    void findById_ExistingTransaction_ReturnsTransaction() {
        // Given
        BankAccount account = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12347")
                .balance(1000.0)
                .build();
        
        entityManager.persistAndFlush(account);

        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(300.0);
        transaction.setDate(LocalDateTime.now());
        transaction.setApprovalCode("DEP-002");
        transaction.setAccount(account);
        
        Transaction savedTransaction = entityManager.persistAndFlush(transaction);

        // When
        Optional<Transaction> result = transactionRepository.findById(savedTransaction.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals(savedTransaction.getId(), result.get().getId());
        assertEquals(300.0, result.get().getAmount(), 0.001);
        assertEquals("DEP-002", result.get().getApprovalCode());
    }

    @Test
    void findById_NonExistentTransaction_ReturnsEmpty() {
        // When
        Optional<Transaction> result = transactionRepository.findById(99999L);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ReturnsAllTransactions() {
        // Given
        BankAccount account = BankAccount.builder()
                .owner("Test Owner")
                .accountNumber("12348")
                .balance(1000.0)
                .build();
        
        entityManager.persistAndFlush(account);

        DepositTransaction depositTransaction = new DepositTransaction();
        depositTransaction.setAmount(500.0);
        depositTransaction.setDate(LocalDateTime.now());
        depositTransaction.setApprovalCode("DEP-003");
        depositTransaction.setAccount(account);

        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction();
        withdrawalTransaction.setAmount(200.0);
        withdrawalTransaction.setDate(LocalDateTime.now());
        withdrawalTransaction.setApprovalCode("WIT-002");
        withdrawalTransaction.setAccount(account);

        entityManager.persistAndFlush(depositTransaction);
        entityManager.persistAndFlush(withdrawalTransaction);

        // When
        List<Transaction> allTransactions = transactionRepository.findAll();

        // Then
        assertTrue(allTransactions.size() >= 2);
        assertTrue(allTransactions.stream().anyMatch(t -> t.getApprovalCode().equals("DEP-003")));
        assertTrue(allTransactions.stream().anyMatch(t -> t.getApprovalCode().equals("WIT-002")));
    }
} 