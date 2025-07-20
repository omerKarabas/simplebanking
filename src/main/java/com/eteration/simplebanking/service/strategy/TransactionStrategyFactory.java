package com.eteration.simplebanking.service.strategy;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.Transaction;
import com.eteration.simplebanking.domain.enums.TransactionType;
import com.eteration.simplebanking.domain.repository.TransactionRepository;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.StrategyNotFoundException;
import com.eteration.simplebanking.exception.TransactionValidationException;
import com.eteration.simplebanking.exception.cosntant.MessageKeys;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;
import com.eteration.simplebanking.util.SecureMaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TransactionStrategyFactory {

    private final Map<TransactionType, TransactionStrategy> strategies;
    private final TransactionRepository transactionRepository;
    private final SecureMaskUtil secureMaskUtil;

    public TransactionStrategyFactory(List<TransactionStrategy> strategyList,
                                      TransactionRepository transactionRepository,
                                      SecureMaskUtil secureMaskUtil) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        TransactionStrategy::getTransactionType,
                        Function.identity()
                ));

        this.transactionRepository = transactionRepository;
        this.secureMaskUtil = secureMaskUtil;

        log.debug("Initialized TransactionStrategyFactory with {} strategies: {}",
                strategies.size(), strategies.keySet());
    }

    public TransactionStrategy getStrategy(TransactionType transactionType) {
        TransactionStrategy strategy = strategies.get(transactionType);
        if (strategy == null) {
            throw new StrategyNotFoundException(MessageKeys.STRATEGY_NOT_FOUND, transactionType);
        }
        return strategy;
    }

    public TransactionStatusResponse executeTransaction(TransactionType transactionType,
                                                        BankAccount account,
                                                        Object... parameters) {
        validateInputs(transactionType, account, parameters);

        TransactionStrategy strategy = getStrategy(transactionType);
        String operationType = strategy.getOperationType();

        try {
            return executeTransactionFlow(strategy, account, operationType, parameters);
        } catch (Exception e) {
            log.debug("[{}][FAILED] Account: {}, Error: {}",
                    operationType, secureMaskUtil.maskAccount(account.getAccountNumber()), e.getMessage());
            // i18n hata mesajı ile fırlat
            throw new RuntimeException(MessageKeys.ERROR_INVALID_TRANSACTION.getKey(), e);
        }
    }

    private TransactionStatusResponse executeTransactionFlow(TransactionStrategy strategy,
                                                             BankAccount account,
                                                             String operationType,
                                                             Object... parameters) throws InsufficientBalanceException {
        Transaction transaction = strategy.createTransaction(parameters);
        validateTransaction(transaction);

        String approvalCode = UUID.randomUUID().toString();
        transaction.setApprovalCode(approvalCode);

        account.post(transaction);
        transactionRepository.save(transaction);

        log.debug("[{}][SUCCESS] Account: {}, Amount: {}, ApprovalCode: {}",
                operationType, secureMaskUtil.maskAccount(account.getAccountNumber()), transaction.getAmount(), secureMaskUtil.maskApprovalCode(approvalCode));

        return new TransactionStatusResponse("OK", approvalCode);
    }

    private void validateInputs(TransactionType transactionType, BankAccount account, Object... parameters) {
        if (transactionType == null) {
            throw new TransactionValidationException(MessageKeys.VALIDATION_TRANSACTION_TYPE_NULL);
        }

        if (account == null) {
            throw new TransactionValidationException(MessageKeys.VALIDATION_BANK_ACCOUNT_NULL);
        }

        if (account.getAccountNumber() == null || account.getAccountNumber().trim().isEmpty()) {
            throw new TransactionValidationException(MessageKeys.VALIDATION_ACCOUNT_NUMBER_NULL_OR_EMPTY);
        }

        if (parameters == null) {
            throw new TransactionValidationException(MessageKeys.VALIDATION_TRANSACTION_PARAMETERS_NULL);
        }
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new TransactionValidationException(MessageKeys.VALIDATION_TRANSACTION_NULL);
        }
    }

    public boolean hasStrategy(TransactionType transactionType) {
        return strategies.containsKey(transactionType);
    }

    public java.util.Set<TransactionType> getAvailableTransactionTypes() {
        return strategies.keySet();
    }
} 