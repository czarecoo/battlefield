package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.common.enums.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.UnitType.*;

@Component
@RequiredArgsConstructor
public class OrderChooser {

    private final ArcherMoveOrder archerMoveOrder;
    private final TransportMoveOrder transportMoveOrder;
    private final ArcherShootOrder archerShootOrder;
    private final CannonShootOrder cannonShootOrder;

    public Order choose(UnitType unitType, CommandType commandType) {
        if (unitType == ARCHER && commandType == MOVE) {
            return archerMoveOrder;
        }
        if (unitType == TRANSPORT && commandType == MOVE) {
            return transportMoveOrder;
        }
        if (unitType == ARCHER && commandType == SHOOT) {
            return archerShootOrder;
        }
        if (unitType == CANNON && commandType == SHOOT) {
            return cannonShootOrder;
        }
        throw new CommandException("Command: %s %s does not exist".formatted(unitType, commandType));
    }
}
