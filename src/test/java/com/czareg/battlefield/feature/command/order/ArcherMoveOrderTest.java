package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.command.order.utils.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArcherMoveOrderTest {

    @Mock
    private UnitService unitService;

    @Mock
    private CooldownConfig cooldownConfig;

    @Mock
    private TargetPositionCalculator targetPositionCalculator;

    @InjectMocks
    private ArcherMoveOrder archerMoveOrder;

    @Test
    void shouldThrowCommandExceptionWhenDetailsSizeIsNotOne() {
        List<CommandDetailsDTO> details = List.of(
                new CommandDetailsDTO(UP, 1),
                new CommandDetailsDTO(RIGHT, 1)
        );

        assertThrows(CommandException.class, () -> archerMoveOrder.validateDetails(details));
    }

    @Test
    void shouldThrowCommandExceptionWhenSquaresIsNotOne() {
        List<CommandDetailsDTO> details = List.of(
                new CommandDetailsDTO(UP, 2)
        );

        assertThrows(CommandException.class, () -> archerMoveOrder.validateDetails(details));
    }

    @Test
    void shouldNotThrowExceptionWhenDetailsAreValid() {
        List<CommandDetailsDTO> details = List.of(
                new CommandDetailsDTO(UP, 1)
        );

        assertDoesNotThrow(() -> archerMoveOrder.validateDetails(details));
    }

    @Test
    void shouldThrowCommandExceptionWhenTargetOutOfBounds() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setBoard(board);
        Unit unit = new Unit();
        Position currentPosition = new Position(2, 2);
        unit.setPosition(currentPosition);
        unit.setGame(game);
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(UP, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(6, 6);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);

        assertThrows(CommandException.class, () -> archerMoveOrder.doExecute(context));
    }

    @Test
    void shouldThrowCommandExceptionWhenTargetIsOccupied() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setId(1L);
        game.setBoard(board);
        Unit unit = new Unit();
        Position currentPosition = new Position(2, 2);
        unit.setPosition(currentPosition);
        unit.setGame(game);
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(Direction.RIGHT, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(3, 2);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);
        when(unitService.existsActiveByPositionAndGameId(targetPosition, 1L)).thenReturn(true);

        assertThrows(CommandException.class, () -> archerMoveOrder.doExecute(context));
    }

    @Test
    void shouldReturnCommandWhenExecutionIsSuccessful() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setId(1L);
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        Position currentPosition = new Position(2, 2);
        when(unit.getPosition()).thenReturn(currentPosition);
        when(unit.getGame()).thenReturn(game);
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(Direction.RIGHT, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(3, 2);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);
        when(unitService.existsActiveByPositionAndGameId(targetPosition, 1L)).thenReturn(false);
        when(cooldownConfig.getArcherMove()).thenReturn(1000);

        Command command = archerMoveOrder.doExecute(context);

        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(currentPosition, command.getBefore());
        assertEquals(targetPosition, command.getTarget());
        assertEquals(MOVE, command.getType());
        assertEquals(1000, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
        verify(unitService, times(1)).existsActiveByPositionAndGameId(targetPosition, 1L);
        verify(unit, times(1)).setPosition(targetPosition);
    }
}