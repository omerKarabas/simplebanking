package com.eteration.simplebanking.service;

import com.eteration.simplebanking.util.SecureMaskUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecureLoggingServiceTest {

    private SecureLoggingService secureLoggingService;

    @BeforeEach
    void setUp() {
        secureLoggingService = new SecureLoggingService();
    }

    @Test
    void testLogTransaction() {
        String accountNumber = "12345678901234";
        String phoneNumber = "55512345678";
        String approvalCode = "67f1aada-637d-4469-a650-3fb6352527ba";
        double amount = 1000.0;
        
        assertDoesNotThrow(() -> {
            secureLoggingService.logTransaction(accountNumber, phoneNumber, approvalCode, amount);
        });
    }

    @Test
    void testLogEncryptedTransaction() {
        String accountNumber = "12345678901234";
        String phoneNumber = "55512345678";
        String approvalCode = "67f1aada-637d-4469-a650-3fb6352527ba";
        double amount = 1000.0;
        
        assertDoesNotThrow(() -> {
            secureLoggingService.logEncryptedTransaction(accountNumber, phoneNumber, approvalCode, amount);
        });
    }

    @Test
    void testLogAuditTrail() {
        String accountNumber = "12345678901234";
        String phoneNumber = "55512345678";
        String approvalCode = "67f1aada-637d-4469-a650-3fb6352527ba";
        
        assertDoesNotThrow(() -> {
            secureLoggingService.logAuditTrail(accountNumber, phoneNumber, approvalCode);
        });
    }

    @Test
    void testGetDecryptedAccount() {
        String originalAccount = "12345678901234";
        String encryptedAccount = SecureMaskUtil.encryptAccount(originalAccount);
        String decryptedAccount = secureLoggingService.getDecryptedAccount(encryptedAccount);
        
        assertEquals(originalAccount, decryptedAccount);
    }

    @Test
    void testGetDecryptedPhone() {
        String originalPhone = "55512345678";
        String encryptedPhone = SecureMaskUtil.encryptPhone(originalPhone);
        String decryptedPhone = secureLoggingService.getDecryptedPhone(encryptedPhone);
        
        assertEquals(originalPhone, decryptedPhone);
    }

    @Test
    void testGetDecryptedApprovalCode() {
        String originalApprovalCode = "67f1aada-637d-4469-a650-3fb6352527ba";
        String encryptedApprovalCode = SecureMaskUtil.encryptApprovalCode(originalApprovalCode);
        String decryptedApprovalCode = secureLoggingService.getDecryptedApprovalCode(encryptedApprovalCode);
        
        assertEquals(originalApprovalCode, decryptedApprovalCode);
    }

    @Test
    void testGetDecryptedAccountWithNull() {
        assertNull(secureLoggingService.getDecryptedAccount(null));
    }

    @Test
    void testGetDecryptedPhoneWithNull() {
        assertNull(secureLoggingService.getDecryptedPhone(null));
    }

    @Test
    void testGetDecryptedApprovalCodeWithNull() {
        assertNull(secureLoggingService.getDecryptedApprovalCode(null));
    }
} 