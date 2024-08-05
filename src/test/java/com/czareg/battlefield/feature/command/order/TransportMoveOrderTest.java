package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.command.order.utils.PathCalculator;
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

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.Direction.RIGHT;
import static com.czareg.battlefield.feature.common.enums.Direction.UP;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransportMoveOrderTest {

    @Mock
    private UnitService unitService;

    @Mock
    private CooldownConfig cooldownConfig;

    @Mock
    private PathCalculator pathCalculator;

    @InjectMocks
    private TransportMoveOrder transportMoveOrder;

    @Test
    void shouldThrowCommandExceptionWhenDetailsIsEmpty() {
        List<CommandDetailsDTO> emptyDetails = List.of();

        assertThrows(CommandException.class, () -> transportMoveOrder.validateDetails(emptyDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenDetailsSizeIsTwo() {
        List<CommandDetailsDTO> multipleDetails = List.of(
                new CommandDetailsDTO(UP, 1),
                new CommandDetailsDTO(RIGHT, 1)
        );

        assertThrows(CommandException.class, () -> transportMoveOrder.validateDetails(multipleDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenSquaresIsZero() {
        List<CommandDetailsDTO> invalidDetailsLow = List.of(new CommandDetailsDTO(UP, 0));

        assertThrows(CommandException.class, () -> transportMoveOrder.validateDetails(invalidDetailsLow));
    }

    @Test
    void shouldThrowCommandExceptionWhenSquaresIsFour() {
        List<CommandDetailsDTO> invalidDetailsHigh = List.of(new CommandDetailsDTO(UP, 4));

        assertThrows(CommandException.class, () -> transportMoveOrder.validateDetails(invalidDetailsHigh));
    }

    @Test
    void shouldNotThrowExceptionWhenDetailsAreValid() {
        List<CommandDetailsDTO> validDetails = List.of(new CommandDetailsDTO(RIGHT, 2));

        assertDoesNotThrow(() -> transportMoveOrder.validateDetails(validDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenTargetOutOfBounds() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        when(unit.getGame()).thenReturn(game);
        Position currentPosition = new Position(2, 2);
        when(unit.getPosition()).thenReturn(currentPosition);
        Position targetPosition = new Position(6, 6);
        when(pathCalculator.calculate(currentPosition, new CommandDetailsDTO(RIGHT, 1))).thenReturn(List.of(targetPosition));
        OrderContext context = new OrderContext(unit, List.of(new CommandDetailsDTO(RIGHT, 1)));

        assertThrows(CommandException.class, () -> transportMoveOrder.doExecute(context));
    }

    @Test
    void shouldUpdateUnitPositionAndCreateCommandWhenExecutionIsSuccessful() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setId(1L);
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        Position startPosition = new Position(2, 2);
        when(unit.getPosition()).thenReturn(startPosition);
        when(unit.getGame()).thenReturn(game);
        Position targetPosition = new Position(3, 2);
        List<Position> targets = List.of(targetPosition);
        CommandDetailsDTO detail = new CommandDetailsDTO(RIGHT, 1);
        when(cooldownConfig.getTransportMove()).thenReturn(500);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(targets);
        OrderContext context = new OrderContext(unit, List.of(detail));
        when(unitService.findActiveByPositionAndGameId(targetPosition, 1L)).thenReturn(Optional.empty());

        Command command = transportMoveOrder.doExecute(context);

        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(startPosition, command.getBefore());
        assertEquals(targetPosition, command.getTarget());
        assertEquals(MOVE, command.getType());
        assertEquals(500, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
        verify(unit, times(1)).setPosition(targetPosition);
    }

    @Test
    void shouldDestroyOneEnemyUnitAndStopBecauseNextTargetIsFriendly() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setId(2L);
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        Position startPosition = new Position(1, 1);
        when(unit.getPosition()).thenReturn(startPosition);
        when(unit.getColor()).thenReturn(Color.WHITE);
        when(unit.getGame()).thenReturn(game);
        Unit firstTarget = mock(Unit.class);
        when(firstTarget.getColor()).thenReturn(Color.BLACK);
        Unit secondTarget = mock(Unit.class);
        when(secondTarget.getColor()).thenReturn(Color.WHITE);
        Position firstTargetPosition = new Position(2, 1);
        Position secondTargetPosition = new Position(3, 1);
        when(unitService.findActiveByPositionAndGameId(firstTargetPosition, 2L)).thenReturn(Optional.of(firstTarget));
        when(unitService.findActiveByPositionAndGameId(secondTargetPosition, 2L)).thenReturn(Optional.of(secondTarget));
        List<Position> positions = List.of(firstTargetPosition, secondTargetPosition);
        CommandDetailsDTO detail = new CommandDetailsDTO(RIGHT, 3);
        when(cooldownConfig.getTransportMove()).thenReturn(5000);
        when(pathCalculator.calculate(startPosition, detail)).thenReturn(positions);
        OrderContext context = new OrderContext(unit, List.of(detail));

        Command command = transportMoveOrder.doExecute(context);

        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(startPosition, command.getBefore());
        assertEquals(firstTargetPosition, command.getTarget());
        assertEquals(MOVE, command.getType());
        assertEquals(5000, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
        verify(firstTarget, times(1)).setStatus(DESTROYED);
        verify(secondTarget, never()).setStatus(DESTROYED);
        verify(unit, times(1)).setPosition(firstTargetPosition);
    }
}