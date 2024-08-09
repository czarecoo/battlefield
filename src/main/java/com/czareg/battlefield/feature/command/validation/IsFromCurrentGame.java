package com.czareg.battlefield.feature.command.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsFromCurrentGameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsFromCurrentGame {

    String message() default "Unit is not from current game";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
