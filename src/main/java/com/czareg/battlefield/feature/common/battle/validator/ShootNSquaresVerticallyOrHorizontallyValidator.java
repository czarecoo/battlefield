package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShootNSquaresVerticallyOrHorizontallyValidator implements CommandDetailsValidator {

    @Override
    public void validate(List<CommandDetails> details) {
        if (details.size() != 1) {
            throw new CommandException("Command requires one detail");
        }
        CommandDetails detail = details.getFirst();
        int squares = detail.getSquares();
        if (squares <= 0) {
            throw new CommandException("Command required at last one square");
        }
    }
}