package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.game.entity.Game;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;
import static utils.CustomAssertions.assertCommand;

@ExtendWith(MockitoExtension.class)
class MoveOneSquareVerticallyOrHorizontallyExecutorTest {

    private static final long GAME_ID = 1L;

    @Mock
    UnitService unitService;
    @Mock
    CooldownConfig cooldownConfig;
    @InjectMocks
    MoveOneSquareVerticallyOrHorizontallyExecutor executor;

    @Test
    void shouldReturnExecutionResultFailureWhenTargetIsOccupied() {
        Position startPosition = new Position(1, 4);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        List<CommandDetails> commandDetails = List.of(new CommandDetails(UP, 1));
        Position targetPosition = new Position(1, 5);
        when(unitService.existsActiveByPositionAndGameId(targetPosition, GAME_ID)).thenReturn(true);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, commandDetails, targetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        assertInstanceOf(ExecutionResult.Failure.class, executionResult);
        verify(unitService, times(1)).existsActiveByPositionAndGameId(targetPosition, GAME_ID);
        assertEquals(startPosition, unit.getPosition());
    }

    @Test
    void shouldReturnExecutionResultSuccessUpdateUnitPositionWhenExecutionIsSuccessful() {
        Position startPosition = new Position(2, 2);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        Position targetPosition = new Position(3, 2);
        List<CommandDetails> commandDetails = List.of(new CommandDetails(RIGHT, 1));
        when(unitService.existsActiveByPositionAndGameId(targetPosition, GAME_ID)).thenReturn(false);
        int cooldown = 12;
        when(cooldownConfig.getMoveOneSquareVerticallyOrHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, commandDetails, targetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, targetPosition, unit, MOVE, cooldown, success.command());
        verify(unitService, times(1)).existsActiveByPositionAndGameId(targetPosition, GAME_ID);
        assertEquals(targetPosition, unit.getPosition());
    }
}