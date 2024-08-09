package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.executor.component.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.battle.executor.logging.BattleLogger;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class ShootNSquaresVerticallyAndNSquaresHorizontallyExecutor implements BattleCommandExecutor {

    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;
    private final TargetPositionCalculator targetPositionCalculator;

    @Override
    public ExecutionResult execute(SpecificCommand specificCommand) {
        Unit unit = specificCommand.getUnit();
        Position current = unit.getPosition();
        Position target = targetPositionCalculator.calculate(current, specificCommand.getDetails());

        Board board = unit.getGame().getBoard();
        if (board.isOutOfBounds(target)) {
            String message = "Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight());
            return new ExecutionResult.Failure(message);
        }

        unitService.findActiveByPositionAndGameId(target, unit.getGame().getId())
                .ifPresentOrElse(targetUnit -> {
                    BattleLogger.logDestroyed(unit, SHOOT, targetUnit);
                    targetUnit.setStatus(DESTROYED);
                }, () -> BattleLogger.logMissed(unit, target));

        Command command = Command.of(current, target, unit, cooldownConfig.getCannonShot(), SHOOT);
        return new ExecutionResult.Success(command);
    }
}
