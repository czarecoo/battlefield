package com.czareg.battlefield.feature.command.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class HasEnumValueValidator implements ConstraintValidator<HasEnumValue, String> {

    private Set<String> availableEnumNames;

    @Override
    public void initialize(HasEnumValue stringEnumeration) {
        Class<? extends Enum<?>> enumSelected = stringEnumeration.enumClass();
        Enum<?>[] enums = enumSelected.getEnumConstants();
        availableEnumNames = Arrays.stream(enums)
                .map(Enum::name)
                .collect(toSet());
    }

    @Override
    public boolean isValid(@NonNull String value, ConstraintValidatorContext cxt) {
        if (!availableEnumNames.contains(value)) {
            cxt.disableDefaultConstraintViolation();
            String customMessage = String.format("Value: %s does match any of valid names: %s", value, availableEnumNames);
            cxt.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}
