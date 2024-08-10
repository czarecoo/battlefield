package com.czareg.battlefield.feature.common.battle.executor.component;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TargetPositionCalculatorTest {

    TargetPositionCalculator targetPositionCalculator = new TargetPositionCalculator();

    @Test
    void shouldReturnTargetPositionWhenMovingRightForTwoSquares() {
        Position startPosition = new Position(1, 1);
        List<CommandDetails> commands = List.of(
                new CommandDetails(RIGHT, 2)
        );

        Position targetPosition = targetPositionCalculator.calculate(startPosition, commands);

        assertEquals(new Position(3, 1), targetPosition);
    }

    @Test
    void shouldReturnTargetPositionWhenMovingInTwoDirections() {
        Position startPosition = new Position(3, 5);
        List<CommandDetails> commands = List.of(
                new CommandDetails(Direction.LEFT, 2),
                new CommandDetails(Direction.DOWN, 3)
        );

        Position targetPosition = targetPositionCalculator.calculate(startPosition, commands);

        assertEquals(new Position(1, 2), targetPosition);
    }
}