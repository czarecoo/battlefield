package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.feature.common.battle.executor.component.PathCalculator;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Status;
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

import static com.czareg.battlefield.feature.common.enums.Color.BLACK;
import static com.czareg.battlefield.feature.common.enums.Color.WHITE;
import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.Direction.DOWN;
import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;
import static utils.CustomAssertions.assertCommand;

@ExtendWith(MockitoExtension.class)
class MoveFromOneToThreeSquaresVerticallyOrHorizontallyExecutorTest {

    private static final long GAME_ID = 1L;

    @Mock
    PathCalculator pathCalculator;
    @Mock
    UnitService unitService;
    @Mock
    CooldownConfig cooldownConfig;
    @InjectMocks
    MoveFromOneToThreeSquaresVerticallyOrHorizontallyExecutor executor;

    @Test
    void shouldReturnExecutionResultSuccessUpdateUnitPositionAndCreateCommandWhenExecutionIsSuccessful() {
        Position startPosition = new Position(2, 2);
        Unit unit = Unit.builder()
                .position(startPosition)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        Position targetPosition = new Position(3, 2);
        CommandDetails detail = new CommandDetails(RIGHT, 1);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(List.of(targetPosition));
        when(unitService.findActiveByPositionAndGameId(targetPosition, GAME_ID)).thenReturn(Optional.empty());
        int cooldown = 1;
        when(cooldownConfig.getMoveFromOneToThreeSquaresVerticallyOrHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(detail), targetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, targetPosition, unit, MOVE, cooldown, success.command());
        verify(unitService, times(1)).findActiveByPositionAndGameId(targetPosition, GAME_ID);
        assertEquals(targetPosition, unit.getPosition());
    }

    @Test
    void shouldReturnExecutionResultSuccessDestroyOneEnemyUnitAndStopBecauseNextTargetIsFriendly() {
        Position startPosition = new Position(1, 1);
        Unit unit = Unit.builder()
                .position(startPosition)
                .color(WHITE)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        Unit firstTarget = mock(Unit.class);
        when(firstTarget.getColor()).thenReturn(BLACK);
        Unit secondTarget = mock(Unit.class);
        when(secondTarget.getColor()).thenReturn(WHITE);
        Position firstTargetPosition = new Position(2, 1);
        Position secondTargetPosition = new Position(3, 1);
        when(unitService.findActiveByPositionAndGameId(firstTargetPosition, GAME_ID)).thenReturn(Optional.of(firstTarget));
        when(unitService.findActiveByPositionAndGameId(secondTargetPosition, GAME_ID)).thenReturn(Optional.of(secondTarget));
        List<Position> positions = List.of(firstTargetPosition, secondTargetPosition);
        CommandDetails detail = new CommandDetails(RIGHT, 2);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(positions);
        int cooldown = 0;
        when(cooldownConfig.getMoveFromOneToThreeSquaresVerticallyOrHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(detail), secondTargetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, firstTargetPosition, unit, MOVE, cooldown, success.command());
        verify(firstTarget, times(1)).setStatus(DESTROYED);
        verify(secondTarget, never()).setStatus(DESTROYED);
        verify(unitService, times(1)).findActiveByPositionAndGameId(firstTargetPosition, GAME_ID);
        verify(unitService, times(1)).findActiveByPositionAndGameId(secondTargetPosition, GAME_ID);
        assertEquals(firstTargetPosition, unit.getPosition());
    }

    @Test
    void shouldReturnExecutionResultSuccessDestroyThreeEnemyUnits() {
        Position startPosition = new Position(1, 1);
        Unit unit = Unit.builder()
                .position(startPosition)
                .color(WHITE)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        Unit firstTarget = mock(Unit.class);
        when(firstTarget.getColor()).thenReturn(BLACK);
        Unit secondTarget = mock(Unit.class);
        when(secondTarget.getColor()).thenReturn(BLACK);
        Unit thirdTarget = mock(Unit.class);
        when(thirdTarget.getColor()).thenReturn(BLACK);
        Position firstTargetPosition = new Position(2, 1);
        Position secondTargetPosition = new Position(3, 1);
        Position thirdTargetPosition = new Position(4, 1);
        when(unitService.findActiveByPositionAndGameId(firstTargetPosition, GAME_ID)).thenReturn(Optional.of(firstTarget));
        when(unitService.findActiveByPositionAndGameId(secondTargetPosition, GAME_ID)).thenReturn(Optional.of(secondTarget));
        when(unitService.findActiveByPositionAndGameId(thirdTargetPosition, GAME_ID)).thenReturn(Optional.of(thirdTarget));
        List<Position> positions = List.of(firstTargetPosition, secondTargetPosition, thirdTargetPosition);
        CommandDetails detail = new CommandDetails(RIGHT, 3);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(positions);
        int cooldown = 134;
        when(cooldownConfig.getMoveFromOneToThreeSquaresVerticallyOrHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(detail), thirdTargetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, thirdTargetPosition, unit, MOVE, cooldown, success.command());
        verify(firstTarget, times(1)).setStatus(DESTROYED);
        verify(secondTarget, times(1)).setStatus(DESTROYED);
        verify(thirdTarget, times(1)).setStatus(DESTROYED);
        verify(unitService, times(1)).findActiveByPositionAndGameId(firstTargetPosition, GAME_ID);
        verify(unitService, times(1)).findActiveByPositionAndGameId(secondTargetPosition, GAME_ID);
        verify(unitService, times(1)).findActiveByPositionAndGameId(thirdTargetPosition, GAME_ID);
        assertEquals(thirdTargetPosition, unit.getPosition());
    }

    @Test
    void shouldReturnExecutionResultSuccessMoveThreeSquaresDownWithoutDestroyingAnyUnit() {
        Position startPosition = new Position(1, 1);
        Unit unit = Unit.builder()
                .position(startPosition)
                .color(WHITE)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        Position firstTargetPosition = new Position(1, 2);
        Position secondTargetPosition = new Position(1, 3);
        Position thirdTargetPosition = new Position(1, 4);
        List<Position> positions = List.of(firstTargetPosition, secondTargetPosition, thirdTargetPosition);
        when(unitService.findActiveByPositionAndGameId(firstTargetPosition, GAME_ID)).thenReturn(Optional.empty());
        when(unitService.findActiveByPositionAndGameId(secondTargetPosition, GAME_ID)).thenReturn(Optional.empty());
        when(unitService.findActiveByPositionAndGameId(thirdTargetPosition, GAME_ID)).thenReturn(Optional.empty());
        CommandDetails detail = new CommandDetails(DOWN, 3);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(positions);
        int cooldown = 233;
        when(cooldownConfig.getMoveFromOneToThreeSquaresVerticallyOrHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(detail), thirdTargetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, thirdTargetPosition, unit, MOVE, cooldown, success.command());
        verify(unitService, times(1)).findActiveByPositionAndGameId(firstTargetPosition, GAME_ID);
        verify(unitService, times(1)).findActiveByPositionAndGameId(secondTargetPosition, GAME_ID);
        verify(unitService, times(1)).findActiveByPositionAndGameId(thirdTargetPosition, GAME_ID);
        assertEquals(thirdTargetPosition, unit.getPosition());
    }

    @Test
    void shouldReturnExecutionResultSuccessAndStayInPlaceWhenEncounteringFriendlyUnit() {
        Position startPosition = new Position(1, 1);
        Unit unit = Unit.builder()
                .position(startPosition)
                .color(WHITE)
                .game(Game.builder()
                        .id(GAME_ID)
                        .build())
                .build();
        Position firstTargetPosition = new Position(1, 2);
        Position secondTargetPosition = new Position(1, 3);
        Position thirdTargetPosition = new Position(1, 4);
        Unit friendlyUnit = mock(Unit.class);
        when(friendlyUnit.getColor()).thenReturn(WHITE);
        List<Position> positions = List.of(firstTargetPosition, secondTargetPosition, thirdTargetPosition);
        when(unitService.findActiveByPositionAndGameId(firstTargetPosition, GAME_ID)).thenReturn(Optional.of(friendlyUnit));
        CommandDetails detail = new CommandDetails(DOWN, 3);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(positions);
        int cooldown = 32312;
        when(cooldownConfig.getMoveFromOneToThreeSquaresVerticallyOrHorizontallyInMillis()).thenReturn(cooldown);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(detail), thirdTargetPosition);

        ExecutionResult executionResult = executor.execute(specificCommand);

        ExecutionResult.Success success = assertInstanceOf(ExecutionResult.Success.class, executionResult);
        assertCommand(startPosition, startPosition, unit, MOVE, cooldown, success.command());
        verify(friendlyUnit, never()).setStatus(any(Status.class));
        verify(unitService, times(1)).findActiveByPositionAndGameId(firstTargetPosition, GAME_ID);
        verify(unitService, never()).findActiveByPositionAndGameId(secondTargetPosition, GAME_ID);
        verify(unitService, never()).findActiveByPositionAndGameId(thirdTargetPosition, GAME_ID);
        assertEquals(startPosition, unit.getPosition());
    }
}
