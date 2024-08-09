package com.czareg.battlefield.feature.command.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistsByUnitIdValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUnitId {

    String message() default "Unit does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
