package com.eteration.simplebanking.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SecureMaskUtilTest {

    @Test
    void testMaskAccount() {
        assertEquals("****", SecureMaskUtil.maskAccount(null));
        assertEquals("****", SecureMaskUtil.maskAccount(""));
        assertEquals("****", SecureMaskUtil.maskAccount("123"));
        assertEquals("****1234", SecureMaskUtil.maskAccount("12345678901234"));
        assertEquals("****5678", SecureMaskUtil.maskAccount("12345678"));
    }

    @Test
    void testMaskPhone() {
        assertEquals("*****", SecureMaskUtil.maskPhone(null));
        assertEquals("*****", SecureMaskUtil.maskPhone(""));
        assertEquals("*****", SecureMaskUtil.maskPhone("123"));
        assertEquals("*****5678", SecureMaskUtil.maskPhone("55512345678"));
        assertEquals("*****1234", SecureMaskUtil.maskPhone("12345678901234"));
    }

    @Test
    void testMaskApprovalCode() {
        assertEquals("****", SecureMaskUtil.maskApprovalCode(null));
        assertEquals("****", SecureMaskUtil.maskApprovalCode(""));
        assertEquals("****", SecureMaskUtil.maskApprovalCode("123"));
        assertEquals("****abcd", SecureMaskUtil.maskApprovalCode("67f1aada-637d-4469-a650-3fb6352527ba"));
        assertEquals("****1234", SecureMaskUtil.maskApprovalCode("test-approval-code-1234"));
    }

    @Test
    void testMaskName() {
        assertEquals("-", SecureMaskUtil.maskName(null));
        assertEquals("-", SecureMaskUtil.maskName(""));
        assertEquals("J.", SecureMaskUtil.maskName("John"));
        assertEquals("K.", SecureMaskUtil.maskName("Kerem Karaca"));
        assertEquals("J.", SecureMaskUtil.maskName("José María García-López"));
    }

    @Test
    void testMaskPayee() {
        assertEquals("-", SecureMaskUtil.maskPayee(null));
        assertEquals("-", SecureMaskUtil.maskPayee(""));
        assertEquals("V.", SecureMaskUtil.maskPayee("Vodafone"));
        assertEquals("T.", SecureMaskUtil.maskPayee("Turkcell"));
        assertEquals("A.", SecureMaskUtil.maskPayee("Alacaklı Şirket"));
    }

    @Test
    void testEncryptAndDecryptAccount() {
        String originalAccount = "12345678901234";
        String encrypted = SecureMaskUtil.encryptAccount(originalAccount);
        String decrypted = SecureMaskUtil.decryptAccount(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalAccount, encrypted);
        assertEquals(originalAccount, decrypted);
    }

    @Test
    void testEncryptAndDecryptPhone() {
        String originalPhone = "55512345678";
        String encrypted = SecureMaskUtil.encryptPhone(originalPhone);
        String decrypted = SecureMaskUtil.decryptPhone(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalPhone, encrypted);
        assertEquals(originalPhone, decrypted);
    }

    @Test
    void testEncryptAndDecryptApprovalCode() {
        String originalApprovalCode = "67f1aada-637d-4469-a650-3fb6352527ba";
        String encrypted = SecureMaskUtil.encryptApprovalCode(originalApprovalCode);
        String decrypted = SecureMaskUtil.decryptApprovalCode(encrypted);
        
        assertNotNull(encrypted);
        assertNotEquals(originalApprovalCode, encrypted);
        assertEquals(originalApprovalCode, decrypted);
    }

    @Test
    void testEncryptNullValues() {
        assertNull(SecureMaskUtil.encryptAccount(null));
        assertNull(SecureMaskUtil.encryptPhone(null));
        assertNull(SecureMaskUtil.encryptApprovalCode(null));
    }

    @Test
    void testDecryptNullValues() {
        assertNull(SecureMaskUtil.decryptAccount(null));
        assertNull(SecureMaskUtil.decryptPhone(null));
        assertNull(SecureMaskUtil.decryptApprovalCode(null));
    }

    @Test
    void testEncryptEmptyValues() {
        assertNull(SecureMaskUtil.encryptAccount(""));
        assertNull(SecureMaskUtil.encryptPhone(""));
        assertNull(SecureMaskUtil.decryptApprovalCode(""));
    }

    @Test
    void testEncryptionConsistency() {
        String account = "12345678901234";
        String encrypted1 = SecureMaskUtil.encryptAccount(account);
        String encrypted2 = SecureMaskUtil.encryptAccount(account);
        
        assertNotEquals(encrypted1, encrypted2);
        
        String decrypted1 = SecureMaskUtil.decryptAccount(encrypted1);
        String decrypted2 = SecureMaskUtil.decryptAccount(encrypted2);
        
        assertEquals(account, decrypted1);
        assertEquals(account, decrypted2);
    }
} 