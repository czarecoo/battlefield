package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.config.advice.exceptions.CooldownException;
import com.czareg.battlefield.feature.command.CommandRepository;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.game.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.game.dto.request.Direction;
import com.czareg.battlefield.feature.game.dto.request.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.game.entity.Board;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.czareg.battlefield.feature.command.entity.CommandType.MOVE;

@Component
@RequiredArgsConstructor
public class ArcherMoveOrder extends Order {

    private final UnitService unitService;
    private final CommandRepository commandRepository;
    private final CooldownConfig cooldownConfig;

    public void execute(OrderContext context) {
        Unit unit = context.getUnit();
        SpecificCommandRequestDTO specificCommandDTO = context.getSpecificCommandDTO();
        List<CommandDetailsDTO> details = specificCommandDTO.getDetails();
        if (details.size() != 1) {
            throw new CommandException("Command requires one detail");
        }
        CommandDetailsDTO detail = details.getFirst();
        Direction direction = detail.getDirection();
        int squares = detail.getSquares();
        if (squares != 1) {
            throw new CommandException("Command is limited to 1 square");
        }

        Position currentPosition = unit.getPosition();
        Position target = currentPosition.calculateTarget(direction, squares);

        Board board = unit.getGame().getBoard();
        if (board.isInvalid(target)) {
            throw new CommandException("Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight()));
        }

        checkCooldown(unit);

        if (unitService.isPositionOccupied(target)) {
            throw new CommandException("Target: %s is occupied".formatted(target));
        }
        unit.setPosition(target);

        Command command = prepareCommand(currentPosition, target, unit);
        commandRepository.save(command);
    }

    private void checkCooldown(Unit unit) {
        commandRepository.findFirstByUnitIdOrderByIdDesc(unit.getId()).ifPresent(lastCommand -> {
            Instant cooldownFinishingTime = lastCommand.getCooldownFinishingTime();
            Instant now = Instant.now();
            if (now.isBefore(cooldownFinishingTime)) {
                throw new CooldownException(Duration.between(now, cooldownFinishingTime));
            }
        });
    }

    private Command prepareCommand(Position currentPosition, Position target, Unit unit) {
        Command command = new Command();
        command.setCommandTime(Instant.now());
        command.setBefore(currentPosition);
        command.setTarget(target);
        command.setUnit(unit);
        command.setCooldownFinishingTime(Instant.now().plusMillis(cooldownConfig.getArcherMove()));
        command.setCommandType(MOVE);
        return command;
    }
}

