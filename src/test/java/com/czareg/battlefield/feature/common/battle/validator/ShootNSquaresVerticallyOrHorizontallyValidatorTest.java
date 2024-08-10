package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.battle.validator.subvalidator.TargetInBoundOfBoardValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShootNSquaresVerticallyOrHorizontallyValidatorTest {

    @Mock
    private TargetInBoundOfBoardValidator targetInBoundOfBoardValidator;
    @InjectMocks
    private ShootNSquaresVerticallyOrHorizontallyValidator validator;

    @Test
    void shouldReturnCommandExceptionWhenNoDetailsProvided() {
        SpecificCommand specificCommand = SpecificCommand.builder()
                .details(List.of())
                .build();

        Optional<CommandException> result = validator.validate(specificCommand);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnCommandExceptionWhenMoreThanOneDetailProvided() {
        SpecificCommand specificCommand = SpecificCommand.builder()
                .details(List.of(
                        new CommandDetails(RIGHT, 2),
                        new CommandDetails(UP, 2)
                ))
                .build();

        Optional<CommandException> result = validator.validate(specificCommand);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnCommandExceptionWhenMovingZeroSquares() {
        SpecificCommand specificCommand = SpecificCommand.builder()
                .details(List.of(
                        new CommandDetails(RIGHT, 0)
                ))
                .build();

        Optional<CommandException> result = validator.validate(specificCommand);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldDelegateValidationToTargetInBoundOfBoardValidatorWhenValid() {
        SpecificCommand specificCommand = SpecificCommand.builder()
                .details(List.of(
                        new CommandDetails(RIGHT, 2)
                ))
                .build();

        when(targetInBoundOfBoardValidator.validate(specificCommand)).thenReturn(Optional.empty());

        Optional<CommandException> result = validator.validate(specificCommand);

        assertTrue(result.isEmpty());
    }
}
