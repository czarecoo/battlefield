package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.command.order.utils.PathCalculator;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class TransportMoveOrder extends Order {

    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;
    private final PathCalculator pathCalculator;

    public Command execute(OrderContext context) {
        List<CommandDetailsDTO> details = context.getDetails();
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
        List<Position> targets = pathCalculator.calculate(currentPosition, detail);

        Position target = processTargetsAndReturnLastValid(targets, unit);

        if (!Objects.equals(currentPosition, target)) {
            unit.setPosition(target);
        }

        return createCommand(currentPosition, target, unit, cooldownConfig.getTransportMove(), MOVE);
    }

    private Position processTargetsAndReturnLastValid(List<Position> targets, Unit unit) {
        Position lastValidTarget = unit.getPosition();
        for (Position target : targets) {
            validateTargetInBounds(target, unit.getGame().getBoard());
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
}