package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.executor.component.PathCalculator;
import com.czareg.battlefield.feature.common.battle.executor.logging.BattleLogger;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class MoveFromOneToThreeSquaresVerticallyOrHorizontallyExecutor implements BattleCommandExecutor {

    private final PathCalculator pathCalculator;
    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;

    @Override
    public ExecutionResult execute(SpecificCommand specificCommand) {
        Unit unit = specificCommand.getUnit();
        Position current = unit.getPosition();
        List<Position> targets = pathCalculator.calculate(current, specificCommand.getDetails().getFirst());
        Position target = processTargetsAndReturnLastValid(targets, unit);
        Command command = Command.of(current, target, unit, cooldownConfig.getMoveFromOneToThreeSquaresVerticallyOrHorizontallyInMillis(), MOVE);
        return new ExecutionResult.Success(command);
    }

    private Position processTargetsAndReturnLastValid(List<Position> targets, Unit unit) {
        for (Position target : targets) {
            Optional<Unit> targetUnitOptional = unitService.findActiveByPositionAndGameId(target, unit.getGame().getId());
            if (targetUnitOptional.isPresent()) {
                Unit targetUnit = targetUnitOptional.get();
                if (targetUnit.getColor() == unit.getColor()) {
                    BattleLogger.triedToMove(unit, target);
                    return unit.getPosition();
                }
                BattleLogger.logDestroyed(unit, MOVE, targetUnit);
                targetUnit.setStatus(DESTROYED);
            } else {
                BattleLogger.logMoved(unit, target);
            }
            unit.setPosition(target);
        }
        return unit.getPosition();
    }
}
