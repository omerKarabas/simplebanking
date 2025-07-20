package com.eteration.simplebanking.domain.validation.validator;

import com.eteration.simplebanking.domain.validation.annotation.PositiveAmount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveAmountValidator implements ConstraintValidator<PositiveAmount, Number> {
    
    @Override
    public void initialize(PositiveAmount constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(Number amount, ConstraintValidatorContext context) {
        if (amount == null) {
            return false;
        }
        
        return amount.doubleValue() > 0;
    }
} 