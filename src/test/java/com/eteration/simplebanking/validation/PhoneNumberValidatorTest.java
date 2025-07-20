package com.eteration.simplebanking.validation;

import com.eteration.simplebanking.domain.validation.annotation.PhoneNumber;
import com.eteration.simplebanking.domain.validation.validator.PhoneNumberValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberValidatorTest {

    private PhoneNumberValidator validator;
    
    @Mock
    private ConstraintValidatorContext context;
    
    @Mock
    private PhoneNumber annotation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new PhoneNumberValidator();
        validator.initialize(annotation);
    }

    @Test
    void testValidPhoneNumber() {
        assertTrue(validator.isValid("5551234567", context));
        assertTrue(validator.isValid("55512345678", context));
    }

    @Test
    void testInvalidPhoneNumber() {
        assertFalse(validator.isValid("555123456", context));
        assertFalse(validator.isValid("555123456789", context));
        assertFalse(validator.isValid("555123456a", context));
        assertFalse(validator.isValid("", context));
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void testPhoneNumberWithSpacesAndDashes() {
        assertFalse(validator.isValid("555-123-4567", context));
        assertFalse(validator.isValid("555 123 4567", context));
        assertFalse(validator.isValid("555 - 123 - 4567", context));
    }
} 