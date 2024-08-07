package com.czareg.battlefield.feature.command.order.utils;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.utils.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TargetPositionCalculatorTest {

    @Test
    void shouldReturnTargetPositionWhenMovingRightForTwoSquares() {
        TargetPositionCalculator targetPositionCalculator = new TargetPositionCalculator();
        Position startPosition = new Position(0, 0);
        List<CommandDetails> commands = List.of(
                new CommandDetails(RIGHT, 2)
        );

        Position targetPosition = targetPositionCalculator.calculate(startPosition, commands);

        assertEquals(new Position(2, 0), targetPosition);
    }

    @Test
    void shouldReturnTargetPositionWhenMovingInTwoDirections() {
        TargetPositionCalculator targetPositionCalculator = new TargetPositionCalculator();
        Position startPosition = new Position(3, 5);
        List<CommandDetails> commands = List.of(
                new CommandDetails(Direction.LEFT, 2),
                new CommandDetails(Direction.DOWN, 3)
        );

        Position targetPosition = targetPositionCalculator.calculate(startPosition, commands);

        assertEquals(new Position(1, 2), targetPosition);
    }
}