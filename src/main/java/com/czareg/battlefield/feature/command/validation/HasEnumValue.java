package com.czareg.battlefield.feature.command.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HasEnumValueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasEnumValue {

    String message() default "Given value does not belong to enum";

    Class<?>[] groups() default {};

    Class<? extends Enum<?>> enumClass();

    Class<? extends Payload>[] payload() default {};
}
