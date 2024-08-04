package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.config.advice.exceptions.CooldownException;
import com.czareg.battlefield.feature.command.CommandRepository;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.game.dto.CommandDetailsDTO;
import com.czareg.battlefield.feature.game.dto.Direction;
import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
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
            throw new CommandException("ARCHER MOVE command requires one detail");
        }
        CommandDetailsDTO detail = details.getFirst();
        Direction direction = detail.getDirection();
        int squares = detail.getSquares();
        if (squares != 1) {
            throw new CommandException("ARCHER MOVE command is limited to 1 square");
        }
        Board board = unit.getGame().getBoard();
        Position currentPosition = unit.getPosition();
        Position target = currentPosition.calculateTarget(direction, squares);
        if (board.isInvalid(target)) {
            throw new CommandException("ARCHER MOVE target: %s is invalid. Board: %s".formatted(target, board));
        }
        if (unitService.isPositionOccupied(target)) {
            throw new CommandException("ARCHER MOVE target: %s is occupied".formatted(target));
        }
        commandRepository.findFirstByOrderByIdDesc().ifPresent(lastCommand -> {
            Instant cooldownFinishingTime = lastCommand.getCooldownFinishingTime();
            Instant now = Instant.now();
            if (now.isBefore(cooldownFinishingTime)) {
                throw new CooldownException(Duration.between(now, cooldownFinishingTime));
            }
        });
        unit.setPosition(target);
        unitService.save(unit);
        Command command = new Command();
        command.setCommandTime(Instant.now());
        command.setBefore(currentPosition);
        command.setTarget(target);
        command.setUnit(unit);
        command.setCooldownFinishingTime(Instant.now().plusMillis(cooldownConfig.getArcherMove()));
        command.setCommandType(MOVE);
        commandRepository.save(command);
    }
}

