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

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class ShootNSquaresVerticallyOrHorizontallyExecutor implements BattleCommandExecutor {

    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;

    @Override
    public ExecutionResult execute(SpecificCommand specificCommand) {
        Unit unit = specificCommand.getUnit();
        Position current = unit.getPosition();
        Position target = specificCommand.getTarget();

        unitService.findActiveByPositionAndGameId(target, unit.getGame().getId())
                .ifPresentOrElse(targetUnit -> {
                    BattleLogger.logDestroyed(unit, SHOOT, targetUnit);
                    targetUnit.setStatus(DESTROYED);
                }, () -> BattleLogger.logMissed(unit, target));

        Command command = Command.of(current, target, unit, cooldownConfig.getShootNSquaresVerticallyOrHorizontallyInMillis(), SHOOT);
        return new ExecutionResult.Success(command);
    }
}
