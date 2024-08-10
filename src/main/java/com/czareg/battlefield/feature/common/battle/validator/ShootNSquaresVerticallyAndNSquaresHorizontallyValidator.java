package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.battle.validator.subvalidator.TargetInBoundOfBoardValidator;
import com.czareg.battlefield.feature.common.enums.Direction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.czareg.battlefield.feature.common.enums.Direction.*;

@Component
@RequiredArgsConstructor
public class ShootNSquaresVerticallyAndNSquaresHorizontallyValidator implements SpecificCommandValidator {

    private final TargetInBoundOfBoardValidator targetInBoundOfBoardValidator;

    @Override
    public Optional<CommandException> validate(SpecificCommand specificCommand) {
        List<CommandDetails> details = specificCommand.getDetails();
        if (details.isEmpty() || details.size() > 2) {
            return Optional.of(new CommandException("Command requires one or two details"));
        }
        if (details.size() == 2) {
            Set<Direction> directions = details.stream()
                    .map(CommandDetails::getDirection)
                    .collect(Collectors.toSet());
            if (directions.size() == 1) {
                return Optional.of(new CommandException("Two details cannot have the same direction"));
            }
            if (directions.containsAll(Set.of(LEFT, RIGHT)) || directions.containsAll(Set.of(UP, DOWN))) {
                return Optional.of(new CommandException("Two details cannot have opposing directions"));
            }
        }
        return targetInBoundOfBoardValidator.validate(specificCommand);
    }
}
