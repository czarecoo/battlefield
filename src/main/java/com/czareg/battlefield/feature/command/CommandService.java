package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.commands.Command;
import com.czareg.battlefield.feature.command.commands.CommandFactory;
import com.czareg.battlefield.feature.command.entity.CommandType;
import com.czareg.battlefield.feature.game.dto.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.unit.UnitRepository;
import com.czareg.battlefield.feature.unit.entity.Unit;
import com.czareg.battlefield.feature.unit.entity.UnitType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.czareg.battlefield.feature.unit.entity.Status.DESTROYED;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandService {

    private final CommandFactory commandFactory;
    private final UnitRepository unitRepository;

    @Transactional
    public void executeSpecificCommand(SpecificCommandRequestDTO specificCommandDTO) {
        Long unitId = specificCommandDTO.getUnitId();
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid unit ID: " + unitId));
        if (unit.getStatus() == DESTROYED) {
            throw new CommandException("Unit destroyed");
        }

        CommandType commandType = specificCommandDTO.getCommand();
        UnitType unitType = unit.getType();

        Command command = commandFactory.create(commandType, unitType);
        command.execute();
    }

    @Transactional
    public void executeRandomCommand(RandomCommandRequestDTO randomCommandDTO) {

    }
}
