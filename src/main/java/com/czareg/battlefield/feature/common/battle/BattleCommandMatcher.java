package com.czareg.battlefield.feature.common.battle;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.unit.BattleUnit;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.common.enums.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BattleCommandMatcher {

    private final List<BattleUnit> battleUnits;

    public BattleCommand match(UnitType unitType, CommandType commandType) {
        return battleUnits.stream()
                .filter(battleUnit -> battleUnit.getUnitType().equals(unitType))
                .flatMap(battleUnit -> battleUnit.getBattleCommands().stream())
                .filter(battleCommand -> battleCommand.getType().equals(commandType))
                .findFirst()
                .orElseThrow(() -> new CommandException("Command: %s %s does not exist".formatted(unitType, commandType)));
    }
}
