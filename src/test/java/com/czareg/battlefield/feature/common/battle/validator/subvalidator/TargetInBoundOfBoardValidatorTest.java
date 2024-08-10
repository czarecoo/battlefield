package com.czareg.battlefield.feature.common.battle.validator.subvalidator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.executor.component.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.game.entity.Game;
import com.czareg.battlefield.feature.unit.entity.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TargetInBoundOfBoardValidatorTest {

    public static final long GAME_ID = 1L;
    @Mock
    private TargetPositionCalculator targetPositionCalculator;
    @InjectMocks
    private TargetInBoundOfBoardValidator validator;

    @Test
    void shouldReturnCommandExceptionWhenTargetIsOutOfBoundsMovingRight() {
        Position startPosition = new Position(5, 5);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .board(new Board(5, 5))
                        .build())
                .build();
        Position targetPosition = new Position(6, 5);
        List<CommandDetails> commandDetails = List.of(new CommandDetails(RIGHT, 1));
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, commandDetails, targetPosition);
        when(targetPositionCalculator.calculate(startPosition, commandDetails)).thenReturn(targetPosition);

        Optional<CommandException> result = validator.validate(specificCommand);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnCommandExceptionWhenTargetIsOutOfBoundsMovingUp() {
        Position startPosition = new Position(1, 5);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .board(new Board(5, 5))
                        .build())
                .build();
        Position targetPosition = new Position(1, 6);
        List<CommandDetails> commandDetails = List.of(new CommandDetails(UP, 1));
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, commandDetails, targetPosition);
        when(targetPositionCalculator.calculate(startPosition, commandDetails)).thenReturn(targetPosition);

        Optional<CommandException> result = validator.validate(specificCommand);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenTargetIsWithinBounds() {
        Position startPosition = new Position(1, 1);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .board(new Board(5, 5))
                        .build())
                .build();
        Position targetPosition = new Position(2, 1);
        List<CommandDetails> commandDetails = List.of(new CommandDetails(RIGHT, 1));
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, commandDetails, targetPosition);
        when(targetPositionCalculator.calculate(startPosition, commandDetails)).thenReturn(targetPosition);

        Optional<CommandException> result = validator.validate(specificCommand);

        assertTrue(result.isEmpty());
    }
}