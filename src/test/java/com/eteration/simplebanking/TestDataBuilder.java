package com.eteration.simplebanking;

import com.eteration.simplebanking.domain.entity.BankAccount;
import com.eteration.simplebanking.domain.entity.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.entity.transaction.WithdrawalTransaction;
import com.eteration.simplebanking.model.dto.request.CreateAccountRequest;
import com.eteration.simplebanking.model.dto.request.TransactionRequest;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.model.dto.response.TransactionStatusResponse;

import java.util.ArrayList;
import java.util.List;

import static com.eteration.simplebanking.TestConstants.*;

/**
 * Test verilerini oluşturmak için kullanılan builder sınıfı.
 * Bu sınıf sayesinde test verilerini tutarlı ve tekrar kullanılabilir şekilde oluşturabiliriz.
 */
public final class TestDataBuilder {
    
    // BankAccount Builder Methods
    public static BankAccount createTestAccount() {
        return BankAccount.builder()
                .accountNumber(TEST_ACCOUNT_NUMBER)
                .owner(TEST_OWNER_NAME)
                .balance(INITIAL_BALANCE)
                .build();
    }
    
    public static BankAccount createTestAccountWithCustomData(String accountNumber, String owner, double balance) {
        return BankAccount.builder()
                .accountNumber(accountNumber)
                .owner(owner)
                .balance(balance)
                .build();
    }
    
    public static BankAccount createTestAccount2() {
        return BankAccount.builder()
                .accountNumber(TEST_ACCOUNT_NUMBER_2)
                .owner(TEST_OWNER_NAME_2)
                .balance(INITIAL_BALANCE)
                .build();
    }
    
    public static BankAccount createTestAccountWithSmallBalance() {
        return BankAccount.builder()
                .accountNumber(TEST_ACCOUNT_NUMBER_4)
                .owner(TEST_OWNER_NAME)
                .balance(SMALL_BALANCE)
                .build();
    }
    
    public static BankAccount createTestAccountWithZeroBalance() {
        return BankAccount.builder()
                .accountNumber(TEST_ACCOUNT_NUMBER_2)
                .owner(TEST_OWNER_NAME_2)
                .balance(ZERO_BALANCE)
                .build();
    }
    
    // Transaction Builder Methods
    public static DepositTransaction createDepositTransaction(double amount) {
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setApprovalCode(DEPOSIT_APPROVAL_CODE);
        return transaction;
    }
    
    public static WithdrawalTransaction createWithdrawalTransaction(double amount) {
        WithdrawalTransaction transaction = new WithdrawalTransaction();
        transaction.setAmount(amount);
        transaction.setApprovalCode(WITHDRAWAL_APPROVAL_CODE);
        return transaction;
    }
    
    public static DepositTransaction createZeroAmountTransaction() {
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(ZERO_BALANCE);
        transaction.setApprovalCode(ZERO_APPROVAL_CODE);
        return transaction;
    }
    
    // Request Builder Methods
    public static CreateAccountRequest createAccountRequest() {
        return new CreateAccountRequest(TEST_OWNER_NAME, TEST_ACCOUNT_NUMBER);
    }
    
    public static CreateAccountRequest createAccountRequestWithCustomData(String owner, String accountNumber) {
        return new CreateAccountRequest(owner, accountNumber);
    }
    
    public static CreateAccountRequest createAccountRequest2() {
        return new CreateAccountRequest(TEST_OWNER_NAME_2, TEST_ACCOUNT_NUMBER_2);
    }
    
    public static TransactionRequest createTransactionRequest(double amount) {
        return new TransactionRequest(amount);
    }
    
    public static TransactionRequest createSmallAmountRequest() {
        return new TransactionRequest(SMALL_AMOUNT);
    }
    
    public static TransactionRequest createMediumAmountRequest() {
        return new TransactionRequest(MEDIUM_AMOUNT);
    }
    
    public static TransactionRequest createLargeAmountRequest() {
        return new TransactionRequest(LARGE_AMOUNT);
    }
    
    public static TransactionRequest createCreditAmountRequest() {
        return new TransactionRequest(CREDIT_AMOUNT);
    }
    
    public static TransactionRequest createDebitAmountRequest() {
        return new TransactionRequest(DEBIT_AMOUNT);
    }
    
    public static TransactionRequest createInsufficientAmountRequest() {
        return new TransactionRequest(INSUFFICIENT_AMOUNT);
    }
    
    // Response Builder Methods
    public static BankAccountResponse createBankAccountResponse() {
        return new BankAccountResponse(TEST_ACCOUNT_NUMBER, TEST_OWNER_NAME, INITIAL_BALANCE, null, null);
    }
    
    public static BankAccountResponse createBankAccountResponseWithCustomData(String accountNumber, String owner, double balance) {
        return new BankAccountResponse(accountNumber, owner, balance, null, null);
    }
    
    public static BankAccountResponse createBankAccountResponse2() {
        return new BankAccountResponse(TEST_ACCOUNT_NUMBER_2, TEST_OWNER_NAME_2, ZERO_BALANCE, null, null);
    }
    
    public static TransactionStatusResponse createTransactionStatusResponse() {
        return new TransactionStatusResponse(SUCCESS_STATUS, TEST_APPROVAL_CODE);
    }
    
    public static TransactionStatusResponse createCreditTransactionResponse() {
        return new TransactionStatusResponse(SUCCESS_STATUS, CREDIT_APPROVAL_CODE);
    }
    
    public static TransactionStatusResponse createDebitTransactionResponse() {
        return new TransactionStatusResponse(SUCCESS_STATUS, DEBIT_APPROVAL_CODE);
    }
    
    public static TransactionStatusResponse createTransactionStatusResponseWithCustomData(String status, String approvalCode) {
        return new TransactionStatusResponse(status, approvalCode);
    }
    
    // List Builder Methods
    public static List<BankAccount> createTestAccountList() {
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(createTestAccount());
        accounts.add(createTestAccount2());
        return accounts;
    }
    
    public static List<TransactionRequest> createTransactionRequestList() {
        List<TransactionRequest> requests = new ArrayList<>();
        requests.add(createSmallAmountRequest());
        requests.add(createMediumAmountRequest());
        requests.add(createLargeAmountRequest());
        return requests;
    }
    
    // Utility class - instantiation yasak
    private TestDataBuilder() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
} 