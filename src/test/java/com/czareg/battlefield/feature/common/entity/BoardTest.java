package com.czareg.battlefield.feature.common.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    @Test
    void shouldReturnTrueWhenPositionIsOutOfLowerBounds() {
        Board board = new Board(5, 5);
        Position position = new Position(0, 0);

        boolean isOutOfBounds = board.isOutOfBounds(position);

        assertTrue(isOutOfBounds);
    }

    @Test
    void shouldReturnTrueWhenPositionIsOutOfUpperBounds() {
        Board board = new Board(5, 5);
        Position position = new Position(6, 6);

        boolean isOutOfBounds = board.isOutOfBounds(position);

        assertTrue(isOutOfBounds);
    }

    @Test
    void shouldReturnFalseWhenPositionIsWithinBounds() {
        Board board = new Board(5, 5);
        Position position = new Position(3, 3);

        boolean isOutOfBounds = board.isOutOfBounds(position);

        assertFalse(isOutOfBounds);
    }

    @Test
    void shouldReturnTrueWhenPositionIsOnLowerBoundary() {
        Board board = new Board(5, 5);
        Position position = new Position(1, 1);

        boolean isOutOfBounds = board.isOutOfBounds(position);

        assertFalse(isOutOfBounds);
    }

    @Test
    void shouldReturnTrueWhenPositionIsOnUpperBoundary() {
        Board board = new Board(5, 5);
        Position position = new Position(5, 5);

        boolean isOutOfBounds = board.isOutOfBounds(position);

        assertFalse(isOutOfBounds);
    }
}