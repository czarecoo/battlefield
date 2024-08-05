package com.czareg.battlefield.feature.command.order.utils;

import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
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
        List<CommandDetailsDTO> commands = List.of(
                new CommandDetailsDTO(RIGHT, 2)
        );

        Position targetPosition = targetPositionCalculator.calculate(startPosition, commands);

        assertEquals(new Position(2, 0), targetPosition);
    }

    @Test
    void shouldReturnTargetPositionWhenMovingInTwoDirections() {
        TargetPositionCalculator targetPositionCalculator = new TargetPositionCalculator();
        Position startPosition = new Position(3, 5);
        List<CommandDetailsDTO> commands = List.of(
                new CommandDetailsDTO(Direction.LEFT, 2),
                new CommandDetailsDTO(Direction.DOWN, 3)
        );

        Position targetPosition = targetPositionCalculator.calculate(startPosition, commands);

        assertEquals(new Position(1, 2), targetPosition);
    }
}