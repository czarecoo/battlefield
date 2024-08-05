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

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;

@Component
@RequiredArgsConstructor
public class ArcherMoveOrder extends Order {

    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;

    public Command execute(OrderContext context) {
        SpecificCommandRequestDTO specificCommandDTO = context.getSpecificCommandDTO();
        List<CommandDetailsDTO> details = specificCommandDTO.getDetails();
        if (details.size() != 1) {
            throw new CommandException("Command requires one detail");
        }
        CommandDetailsDTO detail = details.getFirst();
        int squares = detail.getSquares();
        if (squares != 1) {
            throw new CommandException("Command is limited to 1 square");
        }

        Unit unit = context.getUnit();
        Position currentPosition = unit.getPosition();
        Position target = calculateTarget(currentPosition, details);

        Board board = unit.getGame().getBoard();
        if (board.isOutOfBounds(target)) {
            throw new CommandException("Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight()));
        }

        if (unitService.existsActiveByPosition(target)) {
            throw new CommandException("Target: %s is occupied".formatted(target));
        }
        unit.setPosition(target);

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
        command.setCooldownFinishingAt(Instant.now().plusMillis(cooldownConfig.getArcherMove()));
        command.setType(MOVE);
        return command;
    }
}

