package com.eteration.simplebanking.service.core;

import com.eteration.simplebanking.domain.constant.CacheConstants;
import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.repository.BankAccountRepository;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.mapper.BankAccountMapper;
import com.eteration.simplebanking.service.interfaces.BankAccountService;
import com.eteration.simplebanking.util.SecureMaskUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final SecureMaskUtil secureMaskUtil;

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.BANK_ACCOUNTS_CACHE, allEntries = true)
    public BankAccountResponse createAccount(String owner, String accountNumber) {
        BankAccount account = BankAccount.builder()
                .owner(owner)
                .accountNumber(accountNumber)
                .balance(0.0)
                .build();
        BankAccount savedBankAccount = bankAccountRepository.save(account);
        return bankAccountMapper.toAccountResponse(savedBankAccount);
    }

    @Override
    @Cacheable(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "#accountNumber")
    public BankAccount findAccountByNumber(String accountNumber) {
        log.debug("[CACHE_MISS] Account: {}", secureMaskUtil.maskAccount(accountNumber));
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(MessageKeys.ACCOUNT_NOT_FOUND_WITH_NUMBER, accountNumber));
    }

    @Override
    @Cacheable(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "'response:' + #accountNumber")
    public BankAccountResponse getAccount(String accountNumber) {
        log.debug("[CACHE_MISS_RESPONSE] Account: {}", secureMaskUtil.maskAccount(accountNumber));
        BankAccount account = findAccountByNumber(accountNumber);
        return bankAccountMapper.toAccountResponse(account);
    }

    @Override
    @CacheEvict(value = CacheConstants.BANK_ACCOUNTS_CACHE, key = "#account.accountNumber")
    public BankAccount saveAccount(BankAccount account) {
        return bankAccountRepository.save(account);
    }
} 