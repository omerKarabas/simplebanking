package com.eteration.simplebanking.domain.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheConstants {
    
    public static final String BANK_ACCOUNTS_CACHE = "bankAccounts";
    public static final String TRANSACTIONS_CACHE = "transactions";
} 