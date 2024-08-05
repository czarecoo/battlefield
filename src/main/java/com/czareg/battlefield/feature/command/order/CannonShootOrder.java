package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.command.dto.request.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.Direction.*;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class CannonShootOrder extends Order {

    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;

    public Command execute(OrderContext context) {
        SpecificCommandRequestDTO specificCommandDTO = context.getSpecificCommandDTO();
        List<CommandDetailsDTO> details = specificCommandDTO.getDetails();
        if (details.isEmpty() || details.size() > 2) {
            throw new CommandException("Command requires one or two details");
        }
        if (details.size() == 2) {
            Set<Direction> directions = details.stream()
                    .map(CommandDetailsDTO::getDirection)
                    .collect(Collectors.toSet());
            if (directions.size() == 1) {
                throw new CommandException("Two details cannot have the same direction");
            }
            if (directions.containsAll(Set.of(LEFT, RIGHT)) || directions.containsAll(Set.of(UP, DOWN))) {
                throw new CommandException("Two details cannot have opposing directions");
            }
        }

        Unit unit = context.getUnit();
        Position currentPosition = unit.getPosition();
        Position target = calculateTarget(currentPosition, details);

        Board board = unit.getGame().getBoard();
        if (board.isOutOfBounds(target)) {
            throw new CommandException("Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight()));
        }

        unitService.findActiveByPosition(target).ifPresent(targetUnit -> targetUnit.setStatus(DESTROYED));

        return prepareCommand(currentPosition, target, unit);
    }

    private Position calculateTarget(Position currentPosition, List<CommandDetailsDTO> details) {
        Position target = currentPosition;
        for (CommandDetailsDTO detail : details) {
            Direction direction = detail.getDirection();
            int squares = detail.getSquares();
            target = target.calculateTarget(direction, squares);
        }
        return target;
    }

    private Command prepareCommand(Position currentPosition, Position target, Unit unit) {
        Command command = new Command();
        command.setCreatedAt(Instant.now());
        command.setBefore(currentPosition);
        command.setTarget(target);
        command.setUnit(unit);
        command.setCooldownFinishingAt(Instant.now().plusMillis(cooldownConfig.getCannonShot()));
        command.setType(SHOOT);
        return command;
    }
}
