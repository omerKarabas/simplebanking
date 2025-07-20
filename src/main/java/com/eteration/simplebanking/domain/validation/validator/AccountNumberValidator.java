package com.eteration.simplebanking.domain.validation.validator;

import com.eteration.simplebanking.domain.repository.BankAccountRepository;
import com.eteration.simplebanking.domain.validation.annotation.AccountNumber;
import com.eteration.simplebanking.domain.constant.ValidationRegex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AccountNumberValidator implements ConstraintValidator<AccountNumber, String> {
    private static final Pattern ACCOUNT_PATTERN = Pattern.compile(ValidationRegex.ACCOUNT_NUMBER_PATTERN);

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private boolean checkUniqueness;
    private boolean required;

    @Override
    public void initialize(AccountNumber constraintAnnotation) {
        this.checkUniqueness = constraintAnnotation.checkUniqueness();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required && (value == null || value.trim().isEmpty())) {
            return false;
        }
        if (value != null && !ACCOUNT_PATTERN.matcher(value).matches()) {
            return false;
        }
        if (checkUniqueness && value != null && bankAccountRepository != null) {
            return !bankAccountRepository.existsByAccountNumber(value);
        }
        return true;
    }
} 