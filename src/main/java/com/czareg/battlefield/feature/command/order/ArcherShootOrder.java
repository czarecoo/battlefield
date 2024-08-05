package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.command.order.utils.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;

@Component
@RequiredArgsConstructor
public class ArcherShootOrder extends Order {

    private final UnitService unitService;
    private final CooldownConfig cooldownConfig;
    private final TargetPositionCalculator targetPositionCalculator;

    public Command execute(OrderContext context) {
        List<CommandDetailsDTO> details = context.getDetails();
        if (details.size() != 1) {
            throw new CommandException("Command requires one detail");
        }
        CommandDetailsDTO detail = details.getFirst();
        int squares = detail.getSquares();
        if (squares <= 0) {
            throw new CommandException("Command required at last one square");
        }

        Unit unit = context.getUnit();
        Position currentPosition = unit.getPosition();
        Position target = targetPositionCalculator.calculate(currentPosition, details);

        validateTargetInBounds(target, unit.getGame().getBoard());

        unitService.findActiveByPosition(target).ifPresent(targetUnit -> targetUnit.setStatus(DESTROYED));

        return createCommand(currentPosition, target, unit, cooldownConfig.getArcherShot(), SHOOT);
    }
}
