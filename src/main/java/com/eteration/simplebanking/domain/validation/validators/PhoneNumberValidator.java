package com.eteration.simplebanking.domain.validation.validators;

import com.eteration.simplebanking.domain.validation.annotations.PhoneNumber;
import com.eteration.simplebanking.domain.constants.ValidationRegex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    private static final Pattern PHONE_PATTERN = Pattern.compile(ValidationRegex.PHONE_NUMBER_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(value).matches();
    }
} 