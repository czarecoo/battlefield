package com.czareg.battlefield.feature.common.battle.executor.logging;

import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class BattleLogger {

    public static void logMoved(Unit source, Position target) {
        log.debug("{} {} at {} moved to {}", source.getColor(), source.getType(), source.getPosition(), target);
    }

    public static void logDestroyed(Unit source, CommandType commandType, Unit target) {
        String action = switch (commandType) {
            case MOVE -> "moved";
            case SHOOT -> "shot";
        };
        log.debug("{} {} at {} {} and destroyed {} {} at {}", source.getColor(), source.getType(), source.getPosition(),
                action, target.getColor(), target.getType(), target.getPosition());
    }

    public static void logMissed(Unit source, Position target) {
        log.debug("{} {} at {} shot and missed at {}", source.getColor(), source.getType(), source.getPosition(), target);
    }
}
