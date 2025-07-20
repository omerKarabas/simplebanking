package com.eteration.simplebanking.constant;

import com.eteration.simplebanking.domain.constant.LogConstants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogConstantsTest {

    @Test
    void testLogConstantsValues() {
        assertEquals("****", LogConstants.DEFAULT_MASK.getValue());
        assertEquals("*****", LogConstants.PHONE_MASK.getValue());
        assertEquals("****", LogConstants.APPROVAL_MASK.getValue());
        assertEquals("-", LogConstants.EMPTY_VALUE.getValue());
        assertEquals(".", LogConstants.NAME_SUFFIX.getValue());
    }

    @Test
    void testLogConstantsNotNull() {
        assertNotNull(LogConstants.DEFAULT_MASK);
        assertNotNull(LogConstants.PHONE_MASK);
        assertNotNull(LogConstants.APPROVAL_MASK);
        assertNotNull(LogConstants.EMPTY_VALUE);
        assertNotNull(LogConstants.NAME_SUFFIX);
    }
} 