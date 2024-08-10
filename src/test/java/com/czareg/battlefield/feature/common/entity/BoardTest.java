package com.czareg.battlefield.feature.common.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0, true",
            "6, 6, true",
            "3, 3, false",
            "1, 1, false",
            "5, 5, false"
    })
    void testIsOutOfBounds(int x, int y, boolean expected) {
        Board board = new Board(5, 5);
        Position position = new Position(x, y);

        boolean isOutOfBounds = board.isOutOfBounds(position);

        assertEquals(expected, isOutOfBounds);
    }
}