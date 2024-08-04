package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.entity.CommandType;
import com.czareg.battlefield.feature.command.order.Order;
import com.czareg.battlefield.feature.command.order.OrderChooser;
import com.czareg.battlefield.feature.command.order.OrderContext;
import com.czareg.battlefield.feature.game.dto.request.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.request.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import com.czareg.battlefield.feature.unit.entity.UnitType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.czareg.battlefield.feature.unit.entity.Status.DESTROYED;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandService {

    private final OrderChooser orderChooser;
    private final UnitService unitService;

    @Transactional(isolation = REPEATABLE_READ)
    public void executeSpecificCommand(SpecificCommandRequestDTO specificCommandDTO) {
        Long unitId = specificCommandDTO.getUnitId();
        Unit unit = unitService.findById(unitId)
                .orElseThrow(() -> new CommandException("Invalid unit Id: %s".formatted(unitId)));
        if (unit.getStatus() == DESTROYED) {
            throw new CommandException("Unit destroyed");
        }
        CommandType commandType = specificCommandDTO.getCommand();
        UnitType unitType = unit.getType();
        Order order = orderChooser.choose(unitType, commandType);
        OrderContext orderContext = new OrderContext(unit, specificCommandDTO);
        order.execute(orderContext);
    }

    @Transactional(isolation = REPEATABLE_READ)
    public void executeRandomCommand(RandomCommandRequestDTO randomCommandDTO) {
        //not implemented yet
    }
}
