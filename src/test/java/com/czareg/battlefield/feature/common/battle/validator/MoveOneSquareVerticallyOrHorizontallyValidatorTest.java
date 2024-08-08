package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoveOneSquareVerticallyOrHorizontallyValidatorTest {

    CommandDetailsValidator validator = new MoveOneSquareVerticallyOrHorizontallyValidator();

    @Test
    void shouldThrowCommandExceptionWhenDetailsSizeIsNotOne() {
        List<CommandDetails> details = List.of(
                new CommandDetails(UP, 1),
                new CommandDetails(RIGHT, 1)
        );

        assertThrows(CommandException.class, () -> validator.validate(details));
    }

    @Test
    void shouldThrowCommandExceptionWhenSquaresIsNotOne() {
        List<CommandDetails> details = List.of(
                new CommandDetails(UP, 2)
        );

        assertThrows(CommandException.class, () -> validator.validate(details));
    }

    @Test
    void shouldNotThrowExceptionWhenDetailsAreValid() {
        List<CommandDetails> details = List.of(
                new CommandDetails(UP, 1)
        );

        assertDoesNotThrow(() -> validator.validate(details));
    }
}