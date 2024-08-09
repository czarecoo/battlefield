package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.executor.component.PathCalculator;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Color;
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

import static com.czareg.battlefield.feature.common.enums.Color.WHITE;
import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoveFromOneToThreeSquareVerticallyOrHorizontallyExecutorTest {

    @Mock
    private UnitService unitService;

    @Mock
    private CooldownConfig cooldownConfig;

    @Mock
    private PathCalculator pathCalculator;

    @InjectMocks
    private MoveFromOneToThreeSquareVerticallyOrHorizontallyExecutor executor;

    @Test
    void shouldReturnExecutionResultFailureWhenTargetOutOfBounds() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        when(unit.getGame()).thenReturn(game);
        Position currentPosition = new Position(2, 2);
        when(unit.getPosition()).thenReturn(currentPosition);
        Position targetPosition = new Position(6, 6);
        when(pathCalculator.calculate(currentPosition, new CommandDetails(RIGHT, 1))).thenReturn(List.of(targetPosition));
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(new CommandDetails(RIGHT, 1)));

        ExecutionResult executionResult = executor.execute(specificCommand);

        assertInstanceOf(ExecutionResult.Failure.class, executionResult);
        String message = ((ExecutionResult.Failure) executionResult).message();
        assertNotNull(message);
    }

    @Test
    void shouldReturnExecutionResultSuccessUpdateUnitPositionAndCreateCommandWhenExecutionIsSuccessful() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setId(1L);
        game.setBoard(board);
        Unit unit = new Unit();
        Position startPosition = new Position(2, 2);
        unit.setPosition(startPosition);
        unit.setGame(game);
        Position targetPosition = new Position(3, 2);
        List<Position> targets = List.of(targetPosition);
        CommandDetails detail = new CommandDetails(RIGHT, 1);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(targets);
        when(cooldownConfig.getTransportMove()).thenReturn(500);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(detail));
        when(unitService.findActiveByPositionAndGameId(targetPosition, 1L)).thenReturn(Optional.empty());

        ExecutionResult executionResult = executor.execute(specificCommand);

        assertInstanceOf(ExecutionResult.Success.class, executionResult);
        Command command = ((ExecutionResult.Success) executionResult).command();
        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(targetPosition, unit.getPosition());
        assertEquals(startPosition, command.getBefore());
        assertEquals(targetPosition, command.getTarget());
        assertEquals(MOVE, command.getType());
        assertEquals(500, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
    }

    @Test
    void shouldReturnExecutionResultSuccessDestroyOneEnemyUnitAndStopBecauseNextTargetIsFriendly() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setId(2L);
        game.setBoard(board);
        Unit unit = new Unit();
        Position startPosition = new Position(1, 1);
        unit.setPosition(startPosition);
        unit.setGame(game);
        unit.setColor(WHITE);
        Unit firstTarget = mock(Unit.class);
        when(firstTarget.getColor()).thenReturn(Color.BLACK);
        Unit secondTarget = mock(Unit.class);
        when(secondTarget.getColor()).thenReturn(WHITE);
        Position firstTargetPosition = new Position(2, 1);
        Position secondTargetPosition = new Position(3, 1);
        when(unitService.findActiveByPositionAndGameId(firstTargetPosition, 2L)).thenReturn(Optional.of(firstTarget));
        when(unitService.findActiveByPositionAndGameId(secondTargetPosition, 2L)).thenReturn(Optional.of(secondTarget));
        List<Position> positions = List.of(firstTargetPosition, secondTargetPosition);
        CommandDetails detail = new CommandDetails(RIGHT, 3);
        when(cooldownConfig.getTransportMove()).thenReturn(500);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(positions);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of(detail));

        ExecutionResult executionResult = executor.execute(specificCommand);

        assertInstanceOf(ExecutionResult.Success.class, executionResult);
        Command command = ((ExecutionResult.Success) executionResult).command();
        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(firstTargetPosition, unit.getPosition());
        assertEquals(startPosition, command.getBefore());
        assertEquals(firstTargetPosition, command.getTarget());
        assertEquals(MOVE, command.getType());
        assertEquals(500, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
        verify(firstTarget, times(1)).setStatus(DESTROYED);
        verify(secondTarget, never()).setStatus(DESTROYED);
    }
}