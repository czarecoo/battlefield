package com.czareg.battlefield.feature.command.validation;

import com.czareg.battlefield.feature.unit.UnitService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IsActiveValidator implements ConstraintValidator<IsActive, Long> {

    private final UnitService unitService;

    @Override
    public boolean isValid(Long unitId, ConstraintValidatorContext cxt) {
        if (!unitService.isActiveById(unitId)) {
            cxt.disableDefaultConstraintViolation();
            String customMessage = String.format("Unit with id: %d is not ACTIVE", unitId);
            cxt.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}