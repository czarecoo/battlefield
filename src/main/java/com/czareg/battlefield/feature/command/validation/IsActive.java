package com.czareg.battlefield.feature.command.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsActiveValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsActive {

    String message() default "Unit is not active";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
