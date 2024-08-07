package com.czareg.battlefield.feature.common.battle.command;

import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.executor.ShootNSquaresVerticallyAndNSquaresHorizontallyExecutor;
import com.czareg.battlefield.feature.common.battle.generator.ShootNSquaresVerticallyAndNSquaresHorizontallyGenerator;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.validator.CommandDetailsValidator;
import com.czareg.battlefield.feature.common.battle.validator.ShootNSquaresVerticallyAndNSquaresHorizontallyValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;

@Component
@RequiredArgsConstructor
public class ShootNSquaresVerticallyAndNSquaresHorizontallyCommand implements BattleCommand {

    private final ShootNSquaresVerticallyAndNSquaresHorizontallyValidator validator;
    private final ShootNSquaresVerticallyAndNSquaresHorizontallyExecutor executor;
    private final ShootNSquaresVerticallyAndNSquaresHorizontallyGenerator generator;

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
        return SHOOT;
    }
}
