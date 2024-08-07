package com.czareg.battlefield.feature.common.battle.generator;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.enums.Direction;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.Direction.*;

@Component
@RequiredArgsConstructor
public class ShootNSquaresVerticallyOrHorizontallyGenerator implements SpecificCommandGenerator {

    @Override
    public List<SpecificCommand> generate(Unit unit) {
        Board board = unit.getGame().getBoard();
        List<SpecificCommand> specificCommands = new ArrayList<>();
        specificCommands.addAll(forDirectionAndSquares(unit, LEFT, board.getWidth() - 1));
        specificCommands.addAll(forDirectionAndSquares(unit, RIGHT, board.getWidth() - 1));
        specificCommands.addAll(forDirectionAndSquares(unit, UP, board.getHeight() - 1));
        specificCommands.addAll(forDirectionAndSquares(unit, DOWN, board.getHeight() - 1));
        return specificCommands;
    }

    private List<SpecificCommand> forDirectionAndSquares(Unit unit, Direction direction, int maxSquares) {
        List<SpecificCommand> specificCommands = new ArrayList<>();
        for (int squares = 1; squares <= maxSquares; squares++) {
            CommandDetails commandDetails = new CommandDetails(direction, squares);
            specificCommands.add(new SpecificCommand(unit, SHOOT, List.of(commandDetails)));
        }
        return specificCommands;
    }
}
