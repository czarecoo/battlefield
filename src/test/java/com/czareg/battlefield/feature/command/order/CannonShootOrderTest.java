package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.CooldownConfig;
import com.czareg.battlefield.config.advice.exceptions.CommandException;
import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.command.order.utils.TargetPositionCalculator;
import com.czareg.battlefield.feature.common.entity.Board;
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
import static com.czareg.battlefield.feature.common.enums.Direction.*;
import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CannonShootOrderTest {

    @Mock
    private UnitService unitService;

    @Mock
    private CooldownConfig cooldownConfig;

    @Mock
    private TargetPositionCalculator targetPositionCalculator;

    @InjectMocks
    private CannonShootOrder cannonShootOrder;

    @Test
    void shouldThrowCommandExceptionWhenDetailsSizeIsEmpty() {
        List<CommandDetailsDTO> emptyDetails = List.of();

        assertThrows(CommandException.class, () -> cannonShootOrder.validateDetails(emptyDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenDetailsSizeIsMoreThanTwo() {
        List<CommandDetailsDTO> moreThanTwoDetails = List.of(
                new CommandDetailsDTO(UP, 1),
                new CommandDetailsDTO(RIGHT, 1),
                new CommandDetailsDTO(DOWN, 1)
        );

        assertThrows(CommandException.class, () -> cannonShootOrder.validateDetails(moreThanTwoDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenTwoDetailsHaveSameDirection() {
        List<CommandDetailsDTO> sameDirectionDetails = List.of(
                new CommandDetailsDTO(UP, 1),
                new CommandDetailsDTO(UP, 2)
        );

        assertThrows(CommandException.class, () -> cannonShootOrder.validateDetails(sameDirectionDetails));
    }

    @Test
    void shouldThrowCommandExceptionWhenTwoDetailsHaveOpposingDirections() {
        List<CommandDetailsDTO> opposingDirectionDetails = List.of(
                new CommandDetailsDTO(UP, 1),
                new CommandDetailsDTO(DOWN, 2)
        );

        assertThrows(CommandException.class, () -> cannonShootOrder.validateDetails(opposingDirectionDetails));
    }

    @Test
    void shouldNotThrowExceptionWhenGivenOneValidDetails() {
        List<CommandDetailsDTO> validDetailsOne = List.of(
                new CommandDetailsDTO(UP, 1)
        );

        assertDoesNotThrow(() -> cannonShootOrder.validateDetails(validDetailsOne));
    }

    @Test
    void shouldNotThrowExceptionWhenGivenTwoValidDetails() {
        List<CommandDetailsDTO> validDetails = List.of(
                new CommandDetailsDTO(UP, 1),
                new CommandDetailsDTO(RIGHT, 2)
        );

        assertDoesNotThrow(() -> cannonShootOrder.validateDetails(validDetails));
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
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(UP, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(6, 6);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);

        assertThrows(CommandException.class, () -> cannonShootOrder.doExecute(context));
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
        Unit targetUnit = mock(Unit.class);
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(RIGHT, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(3, 2);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);
        when(unitService.findActiveByPosition(targetPosition)).thenReturn(Optional.of(targetUnit));
        when(cooldownConfig.getCannonShot()).thenReturn(1000);

        Command command = cannonShootOrder.doExecute(context);

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
        List<CommandDetailsDTO> details = List.of(new CommandDetailsDTO(RIGHT, 1));
        OrderContext context = new OrderContext(unit, details);
        Position targetPosition = new Position(3, 2);
        when(targetPositionCalculator.calculate(currentPosition, context.getDetails())).thenReturn(targetPosition);
        when(unitService.findActiveByPosition(targetPosition)).thenReturn(Optional.empty());
        when(cooldownConfig.getCannonShot()).thenReturn(2000);

        Command command = cannonShootOrder.doExecute(context);

        assertNotNull(command);
        assertEquals(unit, command.getUnit());
        assertEquals(currentPosition, command.getBefore());
        assertEquals(targetPosition, command.getTarget());
        assertEquals(SHOOT, command.getType());
        assertEquals(2000, command.getCooldownFinishingAt().toEpochMilli() - command.getCreatedAt().toEpochMilli());
        verify(unitService, times(1)).findActiveByPosition(targetPosition);
        verify(unit, never()).setPosition(targetPosition);
    }
}