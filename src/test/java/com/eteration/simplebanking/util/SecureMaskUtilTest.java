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
    void testMaskAccount() {
        assertEquals("****", secureMaskUtil.maskAccount(null));
        assertEquals("****", secureMaskUtil.maskAccount(""));
        assertEquals("****", secureMaskUtil.maskAccount("123"));
        assertEquals("****1234", secureMaskUtil.maskAccount("12345678901234"));
        assertEquals("****5678", secureMaskUtil.maskAccount("12345678"));
    }

    @Test
    void testMaskPhone() {
        assertEquals("*****", secureMaskUtil.maskPhone(null));
        assertEquals("*****", secureMaskUtil.maskPhone(""));
        assertEquals("*****", secureMaskUtil.maskPhone("123"));
        assertEquals("*****5678", secureMaskUtil.maskPhone("55512345678"));
        assertEquals("*****1234", secureMaskUtil.maskPhone("12345678901234"));
    }

    @Test
    void testMaskApprovalCode() {
        assertEquals("****", secureMaskUtil.maskApprovalCode(null));
        assertEquals("****", secureMaskUtil.maskApprovalCode(""));
        assertEquals("****", secureMaskUtil.maskApprovalCode("123"));
        assertEquals("****27ba", secureMaskUtil.maskApprovalCode("67f1aada-637d-4469-a650-3fb6352527ba"));
        assertEquals("****1234", secureMaskUtil.maskApprovalCode("test-approval-code-1234"));
    }

    @Test
    void testMaskName() {
        assertEquals("-", secureMaskUtil.maskName(null));
        assertEquals("-", secureMaskUtil.maskName(""));
        assertEquals("J.", secureMaskUtil.maskName("John"));
        assertEquals("K.", secureMaskUtil.maskName("Kerem Karaca"));
        assertEquals("J.", secureMaskUtil.maskName("José María García-López"));
    }

    @Test
    void testMaskPayee() {
        assertEquals("-", secureMaskUtil.maskPayee(null));
        assertEquals("-", secureMaskUtil.maskPayee(""));
        assertEquals("V.", secureMaskUtil.maskPayee("Vodafone"));
        assertEquals("T.", secureMaskUtil.maskPayee("Turkcell"));
        assertEquals("A.", secureMaskUtil.maskPayee("Alacaklı Şirket"));
    }

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
    void testEncryptNullValues() {
        assertNull(secureMaskUtil.encryptAccount(null));
        assertNull(secureMaskUtil.encryptPhone(null));
        assertNull(secureMaskUtil.encryptApprovalCode(null));
    }

    @Test
    void testDecryptNullValues() {
        assertNull(secureMaskUtil.decryptAccount(null));
        assertNull(secureMaskUtil.decryptPhone(null));
        assertNull(secureMaskUtil.decryptApprovalCode(null));
    }

    @Test
    void testEncryptEmptyValues() {
        assertNull(secureMaskUtil.encryptAccount(""));
        assertNull(secureMaskUtil.encryptPhone(""));
        assertNull(secureMaskUtil.decryptApprovalCode(""));
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