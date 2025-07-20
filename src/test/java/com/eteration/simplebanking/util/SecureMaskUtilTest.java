package com.eteration.simplebanking.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecureMaskUtilTest {

    @Autowired
    private SecureMaskUtil secureMaskUtil;

    @Test
    void testEncryptAndDecryptAccount() {
        String originalAccount = "12345678901234";
        String encrypted = secureMaskUtil.encryptAccount(originalAccount);
        String decrypted = secureMaskUtil.decryptAccount(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalAccount, encrypted);
        assertEquals(originalAccount, decrypted);
    }

    @Test
    void testEncryptAndDecryptPhone() {
        String originalPhone = "55512345678";
        String encrypted = secureMaskUtil.encryptPhone(originalPhone);
        String decrypted = secureMaskUtil.decryptPhone(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalPhone, encrypted);
        assertEquals(originalPhone, decrypted);
    }

    @Test
    void testEncryptAndDecryptApprovalCode() {
        String originalApprovalCode = "67f1aada-637d-4469-a650-3fb6352527ba";
        String encrypted = secureMaskUtil.encryptApprovalCode(originalApprovalCode);
        String decrypted = secureMaskUtil.decryptApprovalCode(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalApprovalCode, encrypted);
        assertEquals(originalApprovalCode, decrypted);
    }

    @Test
    void testEncryptAndDecryptName() {
        String originalName = "John Doe";
        String encrypted = secureMaskUtil.encryptName(originalName);
        String decrypted = secureMaskUtil.decryptName(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalName, encrypted);
        assertEquals(originalName, decrypted);
    }

    @Test
    void testEncryptAndDecryptPayee() {
        String originalPayee = "Vodafone";
        String encrypted = secureMaskUtil.encryptPayee(originalPayee);
        String decrypted = secureMaskUtil.decryptPayee(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalPayee, encrypted);
        assertEquals(originalPayee, decrypted);
    }

    @Test
    void testEncryptNullValues() {
        assertNull(secureMaskUtil.encryptAccount(null));
        assertNull(secureMaskUtil.encryptPhone(null));
        assertNull(secureMaskUtil.encryptApprovalCode(null));
        assertNull(secureMaskUtil.encryptName(null));
        assertNull(secureMaskUtil.encryptPayee(null));
    }

    @Test
    void testDecryptNullValues() {
        assertNull(secureMaskUtil.decryptAccount(null));
        assertNull(secureMaskUtil.decryptPhone(null));
        assertNull(secureMaskUtil.decryptApprovalCode(null));
        assertNull(secureMaskUtil.decryptName(null));
        assertNull(secureMaskUtil.decryptPayee(null));
    }

    @Test
    void testEncryptEmptyValues() {
        assertNull(secureMaskUtil.encryptAccount(""));
        assertNull(secureMaskUtil.encryptPhone(""));
        assertNull(secureMaskUtil.encryptApprovalCode(""));
        assertNull(secureMaskUtil.encryptName(""));
        assertNull(secureMaskUtil.encryptPayee(""));
    }

    @Test
    void testDecryptEmptyValues() {
        assertNull(secureMaskUtil.decryptAccount(""));
        assertNull(secureMaskUtil.decryptPhone(""));
        assertNull(secureMaskUtil.decryptApprovalCode(""));
        assertNull(secureMaskUtil.decryptName(""));
        assertNull(secureMaskUtil.decryptPayee(""));
    }

    @Test
    void testEncryptionConsistency() {
        String account = "12345678901234";
        String encrypted1 = secureMaskUtil.encryptAccount(account);
        String encrypted2 = secureMaskUtil.encryptAccount(account);
        
        assertEquals(encrypted1, encrypted2);
        
        String decrypted1 = secureMaskUtil.decryptAccount(encrypted1);
        String decrypted2 = secureMaskUtil.decryptAccount(encrypted2);
        
        assertEquals(account, decrypted1);
        assertEquals(account, decrypted2);
    }
} 