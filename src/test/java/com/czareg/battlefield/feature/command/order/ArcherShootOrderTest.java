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
import java.util.Optional;

import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArcherShootOrderTest {

    @Mock
    private UnitService unitService;

    @Mock
    private CooldownConfig cooldownConfig;

    @Mock
    private TargetPositionCalculator targetPositionCalculator;

    @InjectMocks
    private ArcherShootOrder archerShootOrder;

    @Test
    void shouldThrowCommandExceptionWhenDetailsSizeIsNotOne() {
        List<CommandDetailsDTO> details = List.of(
                new CommandDetailsDTO(Direction.UP, 1),
                new CommandDetailsDTO(Direction.RIGHT, 1)
        );

        assertThrows(CommandException.class, () -> archerShootOrder.validateDetails(details));
    }

    @Test
    void shouldThrowCommandExceptionWhenSquaresIsZero() {
        List<CommandDetailsDTO> details = List.of(
                new CommandDetailsDTO(Direction.UP, 0)
        );

        assertThrows(CommandException.class, () -> archerShootOrder.validateDetails(details));
    }

    @Test
    void shouldNotThrowExceptionWhenDetailsAreValid() {
        List<CommandDetailsDTO> details = List.of(
                new CommandDetailsDTO(Direction.UP, 1)
        );

        assertDoesNotThrow(() -> archerShootOrder.validateDetails(details));
    }

    @Test
    void shouldThrowCommandExceptionWhenTargetOutOfBounds() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        Position currentPosition = new Position(2, 2);
        when(unit.getPosition()).thenReturn(currentPosition);
        when(unit.getGame()).thenReturn(game);
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(Direction.UP, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(6, 6);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);

        assertThrows(CommandException.class, () -> archerShootOrder.doExecute(context));
    }

    @Test
    void shouldDestroyTargetUnitWhenFound() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        Position currentPosition = new Position(2, 2);
        when(unit.getPosition()).thenReturn(currentPosition);
        when(unit.getGame()).thenReturn(game);
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(Direction.RIGHT, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(3, 2);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);
        Unit targetUnit = mock(Unit.class);
        when(unitService.findActiveByPosition(targetPosition)).thenReturn(Optional.of(targetUnit));
        when(cooldownConfig.getArcherShot()).thenReturn(1000);

        Command command = archerShootOrder.doExecute(context);

        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(currentPosition, command.getBefore());
        assertEquals(targetPosition, command.getTarget());
        assertEquals(SHOOT, command.getType());
        assertEquals(1000, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
        verify(targetUnit, times(1)).setStatus(DESTROYED);
        verify(unitService, times(1)).findActiveByPosition(targetPosition);
    }

    @Test
    void shouldReturnCommandWhenTargetUnitNotFound() {
        Board board = new Board(5, 5);
        Game game = new Game();
        game.setBoard(board);
        Unit unit = mock(Unit.class);
        Position currentPosition = new Position(2, 2);
        when(unit.getPosition()).thenReturn(currentPosition);
        when(unit.getGame()).thenReturn(game);
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(Direction.RIGHT, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(3, 2);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);
        when(unitService.findActiveByPosition(targetPosition)).thenReturn(Optional.empty());
        when(cooldownConfig.getArcherShot()).thenReturn(12000);

        Command command = archerShootOrder.doExecute(context);

        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(currentPosition, command.getBefore());
        assertEquals(targetPosition, command.getTarget());
        assertEquals(SHOOT, command.getType());
        assertEquals(12000, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
        verify(unitService, times(1)).findActiveByPosition(targetPosition);
        verify(unit, never()).setPosition(targetPosition);
    }
}