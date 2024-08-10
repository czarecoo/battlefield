package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.battle.validator.subvalidator.TargetInBoundOfBoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MoveOneSquareVerticallyOrHorizontallyValidator implements SpecificCommandValidator {

    private final TargetInBoundOfBoardValidator targetInBoundOfBoardValidator;

    @Override
    public Optional<CommandException> validate(SpecificCommand specificCommand) {
        List<CommandDetails> details = specificCommand.getDetails();
        if (details.size() != 1) {
            return Optional.of(new CommandException("Command requires one detail"));
        }
        CommandDetails detail = details.getFirst();
        int squares = detail.getSquares();
        if (squares != 1) {
            return Optional.of(new CommandException("Command is limited to 1 square"));
        }
        return targetInBoundOfBoardValidator.validate(specificCommand);
    }
}
