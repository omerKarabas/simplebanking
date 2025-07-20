package com.eteration.simplebanking.service;

import com.eteration.simplebanking.domain.constant.CacheConstants;
import com.eteration.simplebanking.model.dto.response.BankAccountResponse;
import com.eteration.simplebanking.service.interfaces.BankAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CacheServiceTest {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testCacheConfiguration() {
        // Cache manager'ın doğru yapılandırıldığını kontrol et
        assertNotNull(cacheManager);
        assertTrue(cacheManager.getCacheNames().contains(CacheConstants.BANK_ACCOUNTS_CACHE));
        assertTrue(cacheManager.getCacheNames().contains(CacheConstants.TRANSACTIONS_CACHE));
    }
} 