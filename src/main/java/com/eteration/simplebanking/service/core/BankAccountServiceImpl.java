package com.eteration.simplebanking.service.core;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.repository.BankAccountRepository;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.mapper.BankAccountMapper;
import com.eteration.simplebanking.service.interfaces.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    @Transactional
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
    public BankAccount findAccountByNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(MessageKeys.ACCOUNT_NOT_FOUND_WITH_NUMBER, accountNumber));
    }

    @Override
    public BankAccountResponse getAccount(String accountNumber) {
        BankAccount account = findAccountByNumber(accountNumber);
        return bankAccountMapper.toAccountResponse(account);
    }


    @Override
    public BankAccount saveAccount(BankAccount account) {
        return bankAccountRepository.save(account);
    }
} 