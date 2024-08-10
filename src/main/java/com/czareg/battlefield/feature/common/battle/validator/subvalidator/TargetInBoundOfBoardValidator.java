package com.czareg.battlefield.feature.common.battle.validator.subvalidator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.executor.component.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.battle.validator.SpecificCommandValidator;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TargetInBoundOfBoardValidator implements SpecificCommandValidator {

    private final TargetPositionCalculator targetPositionCalculator;

    @Override
    public Optional<CommandException> validate(SpecificCommand specificCommand) {
        Unit unit = specificCommand.getUnit();
        Position current = unit.getPosition();
        List<CommandDetails> details = specificCommand.getDetails();
        Position target = targetPositionCalculator.calculate(current, details);
        Board board = unit.getGame().getBoard();
        if (board.isOutOfBounds(target)) {
            String message = "Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight());
            return Optional.of(new CommandException(message));
        }
        return Optional.empty();
    }
}
