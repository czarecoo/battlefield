package com.czareg.chessboard.config.validation;

import com.czareg.chessboard.config.NewGameConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class TotalUnitsFitBoardValidator implements ConstraintValidator<TotalUnitsFitBoard, NewGameConfig> {

    @Override
    public boolean isValid(NewGameConfig config, ConstraintValidatorContext cxt) {
        if (config == null) {
            return false;
        }
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