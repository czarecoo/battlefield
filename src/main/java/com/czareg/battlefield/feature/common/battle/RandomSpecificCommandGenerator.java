package com.czareg.battlefield.feature.common.battle;

import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.battle.unit.BattleUnit;
import com.czareg.battlefield.feature.common.util.RandomCollectors;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class RandomSpecificCommandGenerator {

    private final List<BattleUnit> battleUnits;

    public Stream<SpecificCommand> generateAll(Unit unit) {
        return battleUnits.stream()
                .filter(battleUnit -> battleUnit.getUnitType().equals(unit.getType()))
                .flatMap(battleUnit -> battleUnit.getBattleCommands().stream())
                .map(BattleCommand::getSpecificCommandGenerator)
                .flatMap(specificCommandGenerator -> specificCommandGenerator.generate(unit).stream())
                .collect(RandomCollectors.toOptimizedLazyShuffledStream());
    }
}
