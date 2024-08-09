package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveFromOneToThreeSquareVerticallyOrHorizontallyValidatorTest {

    private final CommandDetailsValidator validator = new MoveFromOneToThreeSquareVerticallyOrHorizontallyValidator();

    @Test
    void shouldThrowCommandExceptionWhenDetailsIsEmpty() {
        List<CommandDetails> emptyDetails = List.of();

        assertThrows(CommandException.class, () -> validator.validate(emptyDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenDetailsSizeIsTwo() {
        List<CommandDetails> multipleDetails = List.of(
                new CommandDetails(UP, 1),
                new CommandDetails(RIGHT, 1)
        );

        assertThrows(CommandException.class, () -> validator.validate(multipleDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenSquaresIsZero() {
        List<CommandDetails> invalidDetailsLow = List.of(new CommandDetails(UP, 0));

        assertThrows(CommandException.class, () -> validator.validate(invalidDetailsLow));
    }

    @Test
    void shouldThrowCommandExceptionWhenSquaresIsFour() {
        List<CommandDetails> invalidDetailsHigh = List.of(new CommandDetails(UP, 4));

        assertThrows(CommandException.class, () -> validator.validate(invalidDetailsHigh));
    }

    @Test
    void shouldNotThrowExceptionWhenDetailsAreValid() {
        List<CommandDetails> validDetails = List.of(new CommandDetails(RIGHT, 2));

        assertDoesNotThrow(() -> validator.validate(validDetails));
    }
}