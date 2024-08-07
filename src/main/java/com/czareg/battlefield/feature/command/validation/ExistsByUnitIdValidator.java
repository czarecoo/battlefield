package com.czareg.battlefield.feature.command.validation;

import com.czareg.battlefield.feature.unit.UnitService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByUnitIdValidator implements ConstraintValidator<ExistsByUnitId, Long> {

    private final UnitService unitService;

    @Override
    public boolean isValid(Long unitId, ConstraintValidatorContext cxt) {
        if (!unitService.existsById(unitId)) {
            cxt.disableDefaultConstraintViolation();
            String customMessage = String.format("Unit with id: %d does not exist", unitId);
            cxt.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}