package com.czareg.chessboard.config.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TotalUnitsFitBoardValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TotalUnitsFitBoard {

    String message() default "Current configuration of units exceeds board capacity";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
