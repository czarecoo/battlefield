package com.czareg.battlefield.feature.common.battle.command;

import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.executor.MoveFromOneToThreeSquaresVerticallyOrHorizontallyExecutor;
import com.czareg.battlefield.feature.common.battle.generator.MoveFromOneToThreeSquaresVerticallyOrHorizontallyGenerator;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.validator.MoveFromOneToThreeSquaresVerticallyOrHorizontallyValidator;
import com.czareg.battlefield.feature.common.battle.validator.SpecificCommandValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;

@Component
@RequiredArgsConstructor
public class MoveFromOneToThreeSquaresVerticallyOrHorizontallyCommand implements BattleCommand {

    private final MoveFromOneToThreeSquaresVerticallyOrHorizontallyValidator validator;
    private final MoveFromOneToThreeSquaresVerticallyOrHorizontallyExecutor executor;
    private final MoveFromOneToThreeSquaresVerticallyOrHorizontallyGenerator generator;

    @Override
    public BattleCommandExecutor getBattleCommandExecutor() {
        return executor;
    }

    @Override
    public SpecificCommandValidator getSpecificCommandValidator() {
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
