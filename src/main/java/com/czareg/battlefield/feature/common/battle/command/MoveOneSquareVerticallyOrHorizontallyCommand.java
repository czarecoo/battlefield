package com.czareg.battlefield.feature.common.battle.command;

import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.executor.MoveOneSquareVerticallyOrHorizontallyExecutor;
import com.czareg.battlefield.feature.common.battle.generator.MoveOneSquareVerticallyOrHorizontallyGenerator;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.validator.CommandDetailsValidator;
import com.czareg.battlefield.feature.common.battle.validator.MoveOneSquareVerticallyOrHorizontallyValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;

@Component
@RequiredArgsConstructor
public class MoveOneSquareVerticallyOrHorizontallyCommand implements BattleCommand {

    private final MoveOneSquareVerticallyOrHorizontallyValidator validator;
    private final MoveOneSquareVerticallyOrHorizontallyExecutor executor;
    private final MoveOneSquareVerticallyOrHorizontallyGenerator generator;

    @Override
    public BattleCommandExecutor getBattleCommandExecutor() {
        return executor;
    }

    @Override
    public CommandDetailsValidator getCommandDetailsValidator() {
        return validator;
    }

    @Override
    public SpecificCommandGenerator getSpecificCommandGenerator() {
        return generator;
    }

    @Override
    public CommandType getType() {
        return MOVE;
    }
}
