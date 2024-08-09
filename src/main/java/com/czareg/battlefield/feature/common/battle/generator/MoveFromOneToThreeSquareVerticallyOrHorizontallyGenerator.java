package com.czareg.battlefield.feature.common.battle.generator;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.enums.Direction;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;

@Component
@RequiredArgsConstructor
public class MoveFromOneToThreeSquareVerticallyOrHorizontallyGenerator implements SpecificCommandGenerator {

    @Override
    public List<SpecificCommand> generate(Unit unit) {
        List<SpecificCommand> specificCommands = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            for (int squares = 1; squares <= 3; squares++) {
                CommandDetails commandDetails = new CommandDetails(direction, squares);
                specificCommands.add(new SpecificCommand(unit, MOVE, List.of(commandDetails)));
            }
        }
        return specificCommands;
    }
}
