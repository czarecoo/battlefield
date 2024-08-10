package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.command.component.CooldownChecker;
import com.czareg.battlefield.feature.command.component.SpecificCommandDTOToPojoMapper;
import com.czareg.battlefield.feature.command.dto.request.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.command.dto.request.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.RandomSpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.SpecificCommandProcessor;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandService {

    private final UnitService unitService;
    private final CommandRepository commandRepository;
    private final CooldownChecker cooldownChecker;
    private final RandomSpecificCommandGenerator randomSpecificCommandGenerator;
    private final SpecificCommandProcessor specificCommandProcessor;
    private final SpecificCommandDTOToPojoMapper specificCommandDTOToPojoMapper;


    @Transactional(isolation = REPEATABLE_READ)
    public void executeSpecificCommand(SpecificCommandRequestDTO specificCommandDTO) {
        Long unitId = specificCommandDTO.getUnitId();
        cooldownChecker.check(unitId);
        Unit unit = unitService.getOrThrow(unitId);
        SpecificCommand specificCommand = specificCommandDTOToPojoMapper.map(specificCommandDTO, unit);
        Command command = specificCommandProcessor.processOrThrow(specificCommand);
        commandRepository.save(command);
    }

    @Transactional(isolation = REPEATABLE_READ)
    public void executeRandomCommand(RandomCommandRequestDTO randomCommandDTO) {
        Long unitId = randomCommandDTO.getUnitId();
        cooldownChecker.check(unitId);
        Unit unit = unitService.getOrThrow(unitId);
        List<SpecificCommand> specificCommands = randomSpecificCommandGenerator.generateAll(unit);
        Command command = specificCommands.stream()
                .map(specificCommandProcessor::processOrEmpty)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new CommandException("There are no commands available for this unit"));
        commandRepository.save(command);
    }


}
