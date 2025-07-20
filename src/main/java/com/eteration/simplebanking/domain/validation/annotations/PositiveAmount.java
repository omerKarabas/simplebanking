package com.eteration.simplebanking.domain.validation.annotations;

import com.eteration.simplebanking.domain.validation.validators.PositiveAmountValidator;
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