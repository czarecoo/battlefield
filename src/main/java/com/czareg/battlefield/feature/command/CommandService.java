package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.command.component.CooldownChecker;
import com.czareg.battlefield.feature.command.dto.request.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.command.dto.request.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.BattleCommandMatcher;
import com.czareg.battlefield.feature.common.battle.RandomSpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.common.enums.Direction;
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
    private final BattleCommandMatcher battleCommandMatcher;

    @Transactional(isolation = REPEATABLE_READ)
    public void executeSpecificCommand(SpecificCommandRequestDTO specificCommandDTO) {
        Long unitId = specificCommandDTO.getUnitId();
        cooldownChecker.check(unitId);
        Unit unit = unitService.getOrThrow(unitId);
        SpecificCommand specificCommand = specificCommandDtoToPojo(specificCommandDTO, unit);
        BattleCommand battleCommand = battleCommandMatcher.match(specificCommand);
        Command command = battleCommand.validateAndExecuteOrThrow(specificCommand);
        commandRepository.save(command);
    }

    @Transactional(isolation = REPEATABLE_READ)
    public void executeRandomCommand(RandomCommandRequestDTO randomCommandDTO) {
        Long unitId = randomCommandDTO.getUnitId();
        cooldownChecker.check(unitId);
        Unit unit = unitService.getOrThrow(unitId);

        List<SpecificCommand> specificCommands = randomSpecificCommandGenerator.generateAll(unit);
        Command command = specificCommands.stream()
                .map(specificCommand -> {
                    BattleCommand battleCommand = battleCommandMatcher.match(specificCommand);
                    return battleCommand.tryToValidateAndExecute(specificCommand);
                })
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(() -> new CommandException("There are no commands available for this unit"));
        commandRepository.save(command);
    }

    private static SpecificCommand specificCommandDtoToPojo(SpecificCommandRequestDTO specificCommandDTO, Unit unit) {
        List<CommandDetails> commandDetails = specificCommandDTO.getDetails()
                .stream()
                .map(dto -> new CommandDetails(Direction.valueOf(dto.getDirection()), dto.getSquares()))
                .toList();
        return new SpecificCommand(unit, CommandType.valueOf(specificCommandDTO.getCommand()), commandDetails);
    }
}
