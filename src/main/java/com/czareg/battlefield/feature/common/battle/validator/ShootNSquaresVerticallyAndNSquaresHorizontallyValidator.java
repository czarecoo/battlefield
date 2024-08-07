package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.enums.Direction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.czareg.battlefield.feature.common.enums.Direction.*;

@Component
public class ShootNSquaresVerticallyAndNSquaresHorizontallyValidator implements CommandDetailsValidator {

    @Override
    public void validate(List<CommandDetails> details) {
        if (details.isEmpty() || details.size() > 2) {
            throw new CommandException("Command requires one or two details");
        }
        if (details.size() == 2) {
            Set<Direction> directions = details.stream()
                    .map(CommandDetails::getDirection)
                    .collect(Collectors.toSet());
            if (directions.size() == 1) {
                throw new CommandException("Two details cannot have the same direction");
            }
            if (directions.containsAll(Set.of(LEFT, RIGHT)) || directions.containsAll(Set.of(UP, DOWN))) {
                throw new CommandException("Two details cannot have opposing directions");
            }
        }
    }
}
