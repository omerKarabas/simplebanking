package com.eteration.simplebanking.domain.repository;

import com.eteration.simplebanking.constant.CacheConstants;
import com.eteration.simplebanking.domain.entity.BankAccount;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Cacheable(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "#accountNumber")
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    
    @Cacheable(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "'exists:' + #accountNumber")
    boolean existsByAccountNumber(String accountNumber);
} 