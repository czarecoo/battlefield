package com.czareg.battlefield.feature.common.battle.generator;

import com.czareg.battlefield.feature.common.battle.SpecificCommandFactory;
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
public class ShootNSquaresVerticallyAndNSquaresHorizontallyGenerator implements SpecificCommandGenerator {

    private final ShootNSquaresVerticallyOrHorizontallyGenerator generator;
    private final SpecificCommandFactory specificCommandFactory;

    @Override
    public List<SpecificCommand> generate(Unit unit) {
        Board board = unit.getGame().getBoard();
        List<SpecificCommand> specificCommands = new ArrayList<>();
        specificCommands.addAll(generator.generate(unit));
        specificCommands.addAll(forTwoDirectionsAndSquares(unit, LEFT, UP, board.getWidth() - 1, board.getHeight() - 1));
        specificCommands.addAll(forTwoDirectionsAndSquares(unit, RIGHT, UP, board.getWidth() - 1, board.getHeight() - 1));
        specificCommands.addAll(forTwoDirectionsAndSquares(unit, LEFT, DOWN, board.getWidth() - 1, board.getHeight() - 1));
        specificCommands.addAll(forTwoDirectionsAndSquares(unit, RIGHT, DOWN, board.getWidth() - 1, board.getHeight() - 1));
        return specificCommands;
    }

    private List<SpecificCommand> forTwoDirectionsAndSquares(Unit unit, Direction horizontalDirection, Direction verticalDirection, int maxHorizontalSquares, int maxVerticalSquares) {
        List<SpecificCommand> specificCommands = new ArrayList<>();
        for (int verticalSquares = 1; verticalSquares <= maxVerticalSquares; verticalSquares++) {
            for (int horizontalSquares = 1; horizontalSquares <= maxHorizontalSquares; horizontalSquares++) {
                CommandDetails horizontalCommandDetails = new CommandDetails(horizontalDirection, horizontalSquares);
                CommandDetails verticalCommandDetails = new CommandDetails(verticalDirection, verticalSquares);
                List<CommandDetails> commandDetails = List.of(horizontalCommandDetails, verticalCommandDetails);
                SpecificCommand specificCommand = specificCommandFactory.create(unit, SHOOT, commandDetails);
                specificCommands.add(specificCommand);
            }
        }
        return specificCommands;
    }
}
