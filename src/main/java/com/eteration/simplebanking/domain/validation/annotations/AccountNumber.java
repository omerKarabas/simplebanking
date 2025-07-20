package com.eteration.simplebanking.domain.validation.annotations;

import com.eteration.simplebanking.domain.validation.validators.AccountNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountNumber {
    String message() default "{validation.account.number.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    boolean checkUniqueness() default true;
    
    boolean required() default true;
} 