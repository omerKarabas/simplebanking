package com.eteration.simplebanking.domain.validation.annotation;

import com.eteration.simplebanking.domain.validation.validator.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "{validation.phone.number.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
} 