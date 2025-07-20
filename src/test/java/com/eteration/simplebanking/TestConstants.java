package com.eteration.simplebanking;

public final class TestConstants {
    
    public static final String TEST_ACCOUNT_NUMBER = "12345";
    public static final String TEST_ACCOUNT_NUMBER_2 = "17892";
    public static final String TEST_ACCOUNT_NUMBER_3 = "54321";
    public static final String TEST_ACCOUNT_NUMBER_4 = "67890";
    public static final String NON_EXISTENT_ACCOUNT_NUMBER = "99999";
    
    public static final String TEST_OWNER_NAME = "Test Owner";
    public static final String TEST_OWNER_NAME_2 = "Kerem Karaca";
    public static final String TEST_OWNER_NAME_3 = "New Owner";
    public static final String TEST_OWNER_NAME_4 = "John Doe";
    public static final String TEST_OWNER_NAME_5 = "Jane Smith";
    public static final String TEST_OWNER_NAME_6 = "José María García-López";
    
    public static final String TEST_PHONE_NUMBER = "5551234567";
    public static final String TEST_PHONE_NUMBER_2 = "5559876543";
    
    public static final double INITIAL_BALANCE = 1000.0;
    public static final double SMALL_BALANCE = 500.0;
    public static final double ZERO_BALANCE = 0.0;
    public static final double LARGE_BALANCE = 2000.0;
    
    public static final double SMALL_AMOUNT = 300.0;
    public static final double MEDIUM_AMOUNT = 500.0;
    public static final double LARGE_AMOUNT = 1500.0;
    public static final double CREDIT_AMOUNT = 1000.0;
    public static final double DEBIT_AMOUNT = 50.0;
    public static final double INSUFFICIENT_AMOUNT = 5000.0;
    
    public static final String TEST_APPROVAL_CODE = "test-approval-code";
    public static final String CREDIT_APPROVAL_CODE = "credit-approval-code";
    public static final String DEBIT_APPROVAL_CODE = "debit-approval-code";
    public static final String DEPOSIT_APPROVAL_CODE = "DEP-001";
    public static final String WITHDRAWAL_APPROVAL_CODE = "WIT-001";
    public static final String ZERO_APPROVAL_CODE = "ZERO-001";
    
    public static final String SUCCESS_STATUS = "OK";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance";
    public static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account not found";
    
    public static final String TEST_ENTITY_NAME = "Test";
    public static final String TEST_ENTITY_NAME_1 = "Test1";
    public static final String TEST_ENTITY_NAME_2 = "Test2";
    public static final String DIFFERENT_OWNER = "Different Owner";
    
    public static final Long TEST_ID = 1L;
    public static final Long TEST_VERSION = 1L;
    
    public static final String LONG_ACCOUNT_NUMBER = "12345678901234567890";
    
    private TestConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
} 