package com.eteration.simplebanking.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaskUtilTest {

    @Test
    void testMaskAccount() {
        assertEquals("****", MaskUtil.maskAccount(null));
        assertEquals("****", MaskUtil.maskAccount(""));
        assertEquals("****", MaskUtil.maskAccount("123"));
        assertEquals("****1234", MaskUtil.maskAccount("12345678901234"));
        assertEquals("****5678", MaskUtil.maskAccount("12345678"));
    }

    @Test
    void testMaskPhone() {
        assertEquals("*****", MaskUtil.maskPhone(null));
        assertEquals("*****", MaskUtil.maskPhone(""));
        assertEquals("*****", MaskUtil.maskPhone("123"));
        assertEquals("*****5678", MaskUtil.maskPhone("55512345678"));
        assertEquals("*****1234", MaskUtil.maskPhone("12345678901234"));
    }

    @Test
    void testMaskApprovalCode() {
        assertEquals("****", MaskUtil.maskApprovalCode(null));
        assertEquals("****", MaskUtil.maskApprovalCode(""));
        assertEquals("****", MaskUtil.maskApprovalCode("123"));
        assertEquals("****abcd", MaskUtil.maskApprovalCode("67f1aada-637d-4469-a650-3fb6352527ba"));
        assertEquals("****1234", MaskUtil.maskApprovalCode("test-approval-code-1234"));
    }

    @Test
    void testMaskName() {
        assertEquals("-", MaskUtil.maskName(null));
        assertEquals("-", MaskUtil.maskName(""));
        assertEquals("J.", MaskUtil.maskName("John"));
        assertEquals("K.", MaskUtil.maskName("Kerem Karaca"));
        assertEquals("J.", MaskUtil.maskName("José María García-López"));
    }

    @Test
    void testMaskPayee() {
        assertEquals("-", MaskUtil.maskPayee(null));
        assertEquals("-", MaskUtil.maskPayee(""));
        assertEquals("V.", MaskUtil.maskPayee("Vodafone"));
        assertEquals("T.", MaskUtil.maskPayee("Turkcell"));
        assertEquals("A.", MaskUtil.maskPayee("Alacaklı Şirket"));
    }
} 