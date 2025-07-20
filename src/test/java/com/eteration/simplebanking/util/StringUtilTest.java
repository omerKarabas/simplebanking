package com.eteration.simplebanking.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void testIsBlank() {
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank("   "));
        assertFalse(StringUtil.isBlank("test"));
        assertFalse(StringUtil.isBlank(" test "));
    }
} 