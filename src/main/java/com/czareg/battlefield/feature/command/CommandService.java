package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.config.advice.exceptions.CooldownException;
import com.czareg.battlefield.feature.command.dto.request.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.command.dto.request.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.command.order.Order;
import com.czareg.battlefield.feature.command.order.OrderChooser;
import com.czareg.battlefield.feature.command.order.OrderContext;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.common.enums.UnitType;
import com.czareg.battlefield.feature.game.GameService;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import static com.czareg.battlefield.feature.common.enums.Status.ACTIVE;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandService {

    private final OrderChooser orderChooser;
    private final UnitService unitService;
    private final CommandRepository commandRepository;
    private final GameService gameService;

    @Transactional(isolation = REPEATABLE_READ)
    public void executeSpecificCommand(SpecificCommandRequestDTO specificCommandDTO) {
        Long unitId = specificCommandDTO.getUnitId();
        Unit unit = unitService.findById(unitId)
                .orElseThrow(() -> new CommandException("Unknown unit id: %s".formatted(unitId)));
        checkIfUnitIsActive(unit);
        checkIfUnitBelongsToCurrentGame(unit);
        checkCooldown(unitId);

        CommandType commandType = specificCommandDTO.getCommand();
        UnitType unitType = unit.getType();
        Order order = orderChooser.choose(unitType, commandType);

        OrderContext orderContext = new OrderContext(unit, specificCommandDTO.getDetails());
        Command command = order.execute(orderContext);

        commandRepository.save(command);
    }

    @Transactional(isolation = REPEATABLE_READ)
    public void executeRandomCommand(RandomCommandRequestDTO randomCommandDTO) {
        //not implemented yet
    }

    private static void checkIfUnitIsActive(Unit unit) {
        if (unit.getStatus() != ACTIVE) {
            throw new CommandException("Unit is not active");
        }
    }

    private void checkIfUnitBelongsToCurrentGame(Unit unit) {
        Long currentGameId = gameService.getCurrentGameId();
        Long unitGameId = unit.getGame().getId();
        if (!Objects.equals(currentGameId, unitGameId)) {
            throw new CommandException("Unit does not belong to current game");
        }
    }

    private void checkCooldown(Long unitId) {
        commandRepository.findFirstByUnitIdOrderByIdDesc(unitId).ifPresent(lastCommand -> {
            Instant cooldownFinishingAt = lastCommand.getCooldownFinishingAt();
            Instant now = Instant.now();
            if (now.isBefore(cooldownFinishingAt)) {
                throw new CooldownException(Duration.between(now, cooldownFinishingAt));
            }
        });
    }
}
