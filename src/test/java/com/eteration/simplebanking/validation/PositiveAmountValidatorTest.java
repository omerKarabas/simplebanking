package com.eteration.simplebanking.validation;

import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import com.eteration.simplebanking.domain.validation.validator.PositiveAmountValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PositiveAmountValidatorTest {

    private PositiveAmountValidator validator;
    
    @Mock
    private ConstraintValidatorContext context;
    
    @Mock
    private PositiveAmount annotation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new PositiveAmountValidator();
        validator.initialize(annotation);
    }

    @Test
    void testValidPositiveAmount() {
        assertTrue(validator.isValid(1.0, context));
        assertTrue(validator.isValid(100.0, context));
        assertTrue(validator.isValid(0.01, context));
        assertTrue(validator.isValid(999999.99, context));
        assertTrue(validator.isValid(1, context));
        assertTrue(validator.isValid(100, context));
    }

    @Test
    void testInvalidPositiveAmount() {
        assertFalse(validator.isValid(0.0, context));
        assertFalse(validator.isValid(-1.0, context));
        assertFalse(validator.isValid(-100.0, context));
        assertFalse(validator.isValid(0, context));
        assertFalse(validator.isValid(-1, context));
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void testEdgeCases() {
        assertTrue(validator.isValid(Double.MIN_VALUE, context));
        assertTrue(validator.isValid(Double.MAX_VALUE, context));
        assertFalse(validator.isValid(0.0, context));
        assertFalse(validator.isValid(-0.0, context));
    }

    @Test
    void testDifferentNumberTypes() {
        assertTrue(validator.isValid(1, context));
        assertTrue(validator.isValid(1L, context));
        assertTrue(validator.isValid(1.0f, context));
        assertTrue(validator.isValid(1.0, context));
        
        assertFalse(validator.isValid(0, context));
        assertFalse(validator.isValid(-1, context));
        assertFalse(validator.isValid(-1L, context));
        assertFalse(validator.isValid(-1.0f, context));
        assertFalse(validator.isValid(-1.0, context));
    }
} 