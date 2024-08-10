package com.czareg.battlefield.feature.common.battle.command;

import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.executor.ShootNSquaresVerticallyOrHorizontallyExecutor;
import com.czareg.battlefield.feature.common.battle.generator.ShootNSquaresVerticallyOrHorizontallyGenerator;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.validator.ShootNSquaresVerticallyOrHorizontallyValidator;
import com.czareg.battlefield.feature.common.battle.validator.SpecificCommandValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;

@Component
@RequiredArgsConstructor
public class ShootNSquaresVerticallyOrHorizontallyCommand implements BattleCommand {

    private final ShootNSquaresVerticallyOrHorizontallyValidator validator;
    private final ShootNSquaresVerticallyOrHorizontallyExecutor executor;
    private final ShootNSquaresVerticallyOrHorizontallyGenerator generator;

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
        return SHOOT;
    }
}
