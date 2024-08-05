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

    @Override
    protected void validateDetails(List<CommandDetailsDTO> details) {
        if (details.size() != 1) {
            throw new CommandException("Command requires one detail");
        }
        CommandDetailsDTO detail = details.getFirst();
        int squares = detail.getSquares();
        if (squares <= 0) {
            throw new CommandException("Command required at last one square");
        }
    }

    @Override
    protected Command doExecute(OrderContext context) {
        Unit unit = context.getUnit();
        Position current = unit.getPosition();
        Position target = targetPositionCalculator.calculate(current, context.getDetails());

        validateTargetInBounds(target, unit.getGame().getBoard());

        unitService.findActiveByPositionAndGameId(target, unit.getGame().getId()).ifPresent(targetUnit -> targetUnit.setStatus(DESTROYED));

        return createCommand(current, target, unit, cooldownConfig.getArcherShot(), SHOOT);
    }
}
