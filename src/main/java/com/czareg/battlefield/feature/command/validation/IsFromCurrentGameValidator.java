package com.czareg.battlefield.feature.command.validation;

import com.czareg.battlefield.feature.unit.UnitService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IsFromCurrentGameValidator implements ConstraintValidator<IsFromCurrentGame, Long> {

    private final UnitService unitService;

    @Override
    public boolean isValid(@NonNull Long unitId, ConstraintValidatorContext cxt) {
        if (!unitService.isUnitInCurrentGame(unitId)) {
            cxt.disableDefaultConstraintViolation();
            String customMessage = String.format("Unit with id: %d is not from current game", unitId);
            cxt.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}