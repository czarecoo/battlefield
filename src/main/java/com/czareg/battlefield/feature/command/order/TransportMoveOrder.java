package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.czareg.battlefield.feature.command.entity.CommandType.MOVE;
import static com.czareg.battlefield.feature.unit.entity.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class TransportMoveOrder extends Order {

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
        if (squares < 1 || squares > 3) {
            throw new CommandException("Command requires one, two or three squares");
        }

        Unit unit = context.getUnit();
        Position currentPosition = unit.getPosition();
        List<Position> targets = calculateTargets(currentPosition, detail);

        for (Position target : targets) {
            Board board = unit.getGame().getBoard();
            if (board.isInvalid(target)) {
                throw new CommandException("Target: %s is out of bounds (1 <= x <= %d) && (1 <= y <= %d)".formatted(target, board.getWidth(), board.getHeight()));
            }
        }

        Position lastTarget = processTargetsAndReturnLastValid(targets, unit);

        if (!currentPosition.equals(lastTarget)) {
            unit.setPosition(lastTarget);
        }

        return prepareCommand(currentPosition, lastTarget, unit);
    }

    private List<Position> calculateTargets(Position currentPosition, CommandDetailsDTO detail) {
        List<Position> positions = new ArrayList<>();
        Direction direction = detail.getDirection();
        int squares = detail.getSquares();
        Position target = currentPosition;
        for (int i = 1; i <= squares; i++) {
            target = target.calculateTarget(direction, 1);
            positions.add(target);
        }
        return positions;
    }

    private Position processTargetsAndReturnLastValid(List<Position> targets, Unit unit) {
        Position lastValidTarget = unit.getPosition();
        for (Position target : targets) {
            Optional<Unit> targetUnitOptional = unitService.findActiveByPosition(target);
            if (targetUnitOptional.isPresent()) {
                Unit targetUnit = targetUnitOptional.get();
                if (targetUnit.getColor() == unit.getColor()) {
                    return lastValidTarget;
                }
                targetUnit.setStatus(DESTROYED);
            }
            lastValidTarget = target;
        }
        return lastValidTarget;
    }

    private Command prepareCommand(Position currentPosition, Position target, Unit unit) {
        Command command = new Command();
        command.setCommandTime(Instant.now());
        command.setBefore(currentPosition);
        command.setTarget(target);
        command.setUnit(unit);
        command.setCooldownFinishingTime(Instant.now().plusMillis(cooldownConfig.getTransportMove()));
        command.setCommandType(MOVE);
        return command;
    }
}