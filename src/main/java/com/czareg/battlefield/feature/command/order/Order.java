package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.unit.entity.Unit;

import java.time.Instant;

public abstract class Order {

    public abstract Command execute(OrderContext context);

    protected Command createCommand(Position currentPosition, Position target, Unit unit, int cooldownInMillis, CommandType commandType) {
        Instant now = Instant.now();
        Command command = new Command();
        command.setUnit(unit);
        command.setCreatedAt(now);
        command.setCooldownFinishingAt(now.plusMillis(cooldownInMillis));
        command.setType(commandType);
        command.setBefore(currentPosition);
        command.setTarget(target);
        return command;
    }

    protected void validateTargetInBounds(Position target, Board board) {
        if (board.isOutOfBounds(target)) {
            throw new CommandException("Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight()));
        }
    }
}
