package com.czareg.battlefield.feature.common.entity;

import com.czareg.battlefield.feature.common.enums.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    @Test
    void shouldReturnNewPositionWhenMovingRight() {
        Position startPosition = new Position(0, 0);
        Direction direction = Direction.RIGHT;
        int squares = 2;

        Position newPosition = startPosition.calculateNewPosition(direction, squares);

        assertEquals(new Position(2, 0), newPosition);
    }

    @Test
    void shouldReturnNewPositionWhenMovingUp() {
        Position startPosition = new Position(0, 0);
        Direction direction = Direction.UP;
        int squares = 3;

        Position newPosition = startPosition.calculateNewPosition(direction, squares);

        assertEquals(new Position(0, 3), newPosition);
    }

    @Test
    void shouldReturnNewPositionWhenMovingDown() {
        Position startPosition = new Position(0, 0);
        Direction direction = Direction.DOWN;
        int squares = 2;

        Position newPosition = startPosition.calculateNewPosition(direction, squares);

        assertEquals(new Position(0, -2), newPosition);
    }

    @Test
    void shouldReturnNewPositionWhenMovingLeft() {
        Position startPosition = new Position(0, 0);
        Direction direction = Direction.LEFT;
        int squares = 3;

        Position newPosition = startPosition.calculateNewPosition(direction, squares);

        assertEquals(new Position(-3, 0), newPosition);
    }
}