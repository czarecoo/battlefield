package com.czareg.battlefield.feature.common.battle;

import com.czareg.battlefield.feature.common.battle.executor.component.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecificCommandFactory {

    private final TargetPositionCalculator targetPositionCalculator;

    public SpecificCommand create(Unit unit, CommandType commandType, List<CommandDetails> commandDetails) {
        Position target = targetPositionCalculator.calculate(unit.getPosition(), commandDetails);
        return new SpecificCommand(unit, commandType, commandDetails, target);
    }
}
