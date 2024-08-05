package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.command.order.utils.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    private final TargetPositionCalculator targetPositionCalculator;

    public Command execute(OrderContext context) {
        List<CommandDetailsDTO> details = context.getDetails();
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
        Position target = targetPositionCalculator.calculate(currentPosition, details);

        validateTargetInBounds(target, unit.getGame().getBoard());

        unitService.findActiveByPosition(target).ifPresent(targetUnit -> targetUnit.setStatus(DESTROYED));

        return createCommand(currentPosition, target, unit, cooldownConfig.getCannonShot(), SHOOT);
    }
}
