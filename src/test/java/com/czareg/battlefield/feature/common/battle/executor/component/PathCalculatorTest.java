package com.czareg.battlefield.feature.common.battle.executor.component;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.entity.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PathCalculatorTest {

    PathCalculator pathCalculator = new PathCalculator();

    @Test
    void shouldReturnListOfTwoPositionsWhenMovingRightForTwoSquares() {
        Position startPosition = new Position(10, 10);
        CommandDetails commandDetails = new CommandDetails(RIGHT, 2);

        List<Position> positions = pathCalculator.calculate(startPosition, commandDetails);

        assertEquals(2, positions.size());
        assertEquals(new Position(11, 10), positions.get(0));
        assertEquals(new Position(12, 10), positions.get(1));
    }

    @Test
    void shouldReturnListOfThreePositionsWhenMovingUpForThreeSquares() {
        Position startPosition = new Position(4, 4);
        CommandDetails commandDetails = new CommandDetails(UP, 3);

        List<Position> positions = pathCalculator.calculate(startPosition, commandDetails);

        assertEquals(3, positions.size());
        assertEquals(new Position(4, 5), positions.get(0));
        assertEquals(new Position(4, 6), positions.get(1));
        assertEquals(new Position(4, 7), positions.get(2));
    }
}