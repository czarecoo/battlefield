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
import com.czareg.battlefield.feature.unit.entity.Color;
import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.czareg.battlefield.feature.command.entity.CommandType.SHOOT;
import static com.czareg.battlefield.feature.game.dto.request.Direction.*;
import static com.czareg.battlefield.feature.unit.entity.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class CannonShootOrder extends Order {

    private final UnitService unitService;
    private final CommandRepository commandRepository;
    private final CooldownConfig cooldownConfig;

    public void execute(OrderContext context) {
        Unit unit = context.getUnit();
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

        Position currentPosition = unit.getPosition();
        Position target = calculateTarget(currentPosition, details);

        Board board = unit.getGame().getBoard();
        if (board.isInvalid(target)) {
            throw new CommandException("Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight()));
        }

        checkCooldown(unit);

        unitService.findByPosition(target).ifPresent(targetUnit -> {
            Color targetColor = targetUnit.getColor();
            if (targetColor == unit.getColor()) {
                throw new CommandException("Target: %s is occupied by friendly unit. Cannot shoot");
            }
            if (targetUnit.getStatus() == DESTROYED) {
                return;
            }
            targetUnit.setStatus(DESTROYED);
        });

        Command command = prepareCommand(currentPosition, target, unit);
        commandRepository.save(command);
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
        command.setCooldownFinishingTime(Instant.now().plusMillis(cooldownConfig.getCannonShot()));
        command.setCommandType(SHOOT);
        return command;
    }
}
