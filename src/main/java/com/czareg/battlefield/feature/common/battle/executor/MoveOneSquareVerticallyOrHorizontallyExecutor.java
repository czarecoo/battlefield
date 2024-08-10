package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.executor.logging.BattleLogger;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;

@Component
@RequiredArgsConstructor
public class MoveOneSquareVerticallyOrHorizontallyExecutor implements BattleCommandExecutor {

    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;

    @Override
    public ExecutionResult execute(SpecificCommand specificCommand) {
        Unit unit = specificCommand.getUnit();
        Position current = unit.getPosition();
        Position target = specificCommand.getTarget();

        if (unitService.existsActiveByPositionAndGameId(target, unit.getGame().getId())) {
            return new ExecutionResult.Failure("Target: %s is occupied".formatted(target));
        }
        BattleLogger.logMoved(unit, target);
        unit.setPosition(target);

        Command command = Command.of(current, target, unit, cooldownConfig.getMoveOneSquareVerticallyOrHorizontallyInMillis(), MOVE);
        return new ExecutionResult.Success(command);
    }
}
