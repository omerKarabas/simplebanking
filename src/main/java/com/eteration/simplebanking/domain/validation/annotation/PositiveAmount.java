package com.eteration.simplebanking.domain.validation.annotation;

import com.eteration.simplebanking.domain.validation.validator.PositiveAmountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositiveAmountValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveAmount {
    String message() default "{validation.amount.positive}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 