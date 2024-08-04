package com.czareg.battlefield.feature.command.commands;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.entity.CommandType;
import com.czareg.battlefield.feature.unit.entity.UnitType;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.command.entity.CommandType.MOVE;
import static com.czareg.battlefield.feature.command.entity.CommandType.SHOOT;
import static com.czareg.battlefield.feature.unit.entity.UnitType.*;

@Component
public class CommandFactory {

    public Command create(CommandType commandType, UnitType unitType) {
        if (commandType == MOVE && unitType == ARCHER) {
            return new ArcherMoveCommand();
        }
        if (commandType == MOVE && unitType == TRANSPORT) {
            return new TransportMoveCommand();
        }
        if (commandType == SHOOT && unitType == ARCHER) {
            return new ArcherShootCommand();
        }
        if (commandType == SHOOT && unitType == CANNON) {
            return new CannonShootCommand();
        }
        throw new CommandException("Command: %s %s does not exist".formatted(commandType, unitType));
    }
}
