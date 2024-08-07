package com.czareg.battlefield.feature.common.battle.command;

import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.executor.MoveFromOneToThreeSquareVerticallyOrHorizontallyExecutor;
import com.czareg.battlefield.feature.common.battle.generator.MoveFromOneToThreeSquareVerticallyOrHorizontallyGenerator;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.validator.CommandDetailsValidator;
import com.czareg.battlefield.feature.common.battle.validator.MoveFromOneToThreeSquareVerticallyOrHorizontallyValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;

@Component
@RequiredArgsConstructor
public class MoveFromOneToThreeSquareVerticallyOrHorizontallyCommand implements BattleCommand {

    private final MoveFromOneToThreeSquareVerticallyOrHorizontallyValidator validator;
    private final MoveFromOneToThreeSquareVerticallyOrHorizontallyExecutor executor;
    private final MoveFromOneToThreeSquareVerticallyOrHorizontallyGenerator generator;

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
