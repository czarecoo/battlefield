package com.czareg.battlefield.feature.command.order.utils;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.utils.PathCalculator;
import com.czareg.battlefield.feature.common.entity.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PathCalculatorTest {

    @Test
    void shouldReturnListOfTwoPositionsWhenMovingRightForTwoSquares() {
        PathCalculator pathCalculator = new PathCalculator();
        Position startPosition = new Position(0, 0);
        CommandDetails commandDetails = new CommandDetails(RIGHT, 2);

        List<Position> positions = pathCalculator.calculate(startPosition, commandDetails);

        assertEquals(2, positions.size());
        assertEquals(new Position(1, 0), positions.get(0));
        assertEquals(new Position(2, 0), positions.get(1));
    }

    @Test
    void shouldReturnListOfThreePositionsWhenMovingUpForThreeSquares() {
        PathCalculator pathCalculator = new PathCalculator();
        Position startPosition = new Position(0, 0);
        CommandDetails commandDetails = new CommandDetails(UP, 3);

        List<Position> positions = pathCalculator.calculate(startPosition, commandDetails);

        assertEquals(3, positions.size());
        assertEquals(new Position(0, 1), positions.get(0));
        assertEquals(new Position(0, 2), positions.get(1));
        assertEquals(new Position(0, 3), positions.get(2));
    }
}