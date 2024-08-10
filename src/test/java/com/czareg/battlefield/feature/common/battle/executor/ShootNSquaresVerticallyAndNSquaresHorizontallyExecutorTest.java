package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.config.CooldownConfig;
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
import java.util.Optional;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;
import static utils.CustomAssertions.assertCommand;

@ExtendWith(MockitoExtension.class)
class ShootNSquaresVerticallyAndNSquaresHorizontallyExecutorTest {

    private static final long GAME_ID = 1L;

    @Mock
    UnitService unitService;
    @Mock
    CooldownConfig cooldownConfig;
    @InjectMocks
    ShootNSquaresVerticallyAndNSquaresHorizontallyExecutor executor;

    @Test
    void shouldReturnExecutionResultSuccessAndDestroyEnemyUnitWhenTargetIsOccupiedByEnemy() {
        Position startPosition = new Position(2, 2);
        Position targetPosition = new Position(3, 3);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        Unit enemyUnit = mock(Unit.class);
        when(unitService.findActiveByPositionAndGameId(targetPosition, GAME_ID)).thenReturn(Optional.of(enemyUnit));
        int cooldown = 11000;
        when(cooldownConfig.getShootNSquaresVerticallyAndNSquaresHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, SHOOT, List.of(), targetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, targetPosition, unit, SHOOT, cooldown, success.command());
        verify(unitService, times(1)).findActiveByPositionAndGameId(targetPosition, GAME_ID);
        verify(enemyUnit, times(1)).setStatus(DESTROYED);
    }

    @Test
    void shouldReturnExecutionResultSuccessWhenTargetIsUnoccupied() {
        Position startPosition = new Position(2, 2);
        Position targetPosition = new Position(3, 3);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        when(unitService.findActiveByPositionAndGameId(targetPosition, GAME_ID)).thenReturn(Optional.empty());
        int cooldown = 500;
        when(cooldownConfig.getShootNSquaresVerticallyAndNSquaresHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, SHOOT, List.of(), targetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, targetPosition, unit, SHOOT, cooldown, success.command());
        verify(unitService, times(1)).findActiveByPositionAndGameId(targetPosition, GAME_ID);
    }
}
