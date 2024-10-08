package com.czareg.battlefield.config.validation;

import com.czareg.battlefield.config.NewGameConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TotalUnitsFitBoardValidator implements ConstraintValidator<TotalUnitsFitBoard, NewGameConfig> {

    @Override
    public boolean isValid(@NonNull NewGameConfig config, ConstraintValidatorContext cxt) {
        int totalSpace = config.getBoardWidth() * config.getBoardHeight();
        int totalUnits = (config.getArcherCount() + config.getTransportCount() + config.getCannonCount()) * 2;
        if (totalSpace < totalUnits) {
            cxt.disableDefaultConstraintViolation();
            String customMessage = String.format("Current configuration of units exceeds board capacity. Total space: %d, total units: %d.", totalSpace, totalUnits);
            cxt.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}