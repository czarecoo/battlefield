package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.config.advice.exceptions.CommandException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.UnitType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderChooserTest {

    @Mock
    private ArcherMoveOrder archerMoveOrder;

    @Mock
    private TransportMoveOrder transportMoveOrder;

    @Mock
    private ArcherShootOrder archerShootOrder;

    @Mock
    private CannonShootOrder cannonShootOrder;

    @InjectMocks
    private OrderChooser orderChooser;

    @Test
    void shouldReturnArcherMoveOrderWhenUnitIsArcherAndCommandIsMove() {
        Order result = orderChooser.choose(ARCHER, MOVE);

        assertEquals(archerMoveOrder, result);
    }

    @Test
    void shouldReturnTransportMoveOrderWhenUnitIsTransportAndCommandIsMove() {
        Order result = orderChooser.choose(TRANSPORT, MOVE);

        assertEquals(transportMoveOrder, result);
    }

    @Test
    void shouldReturnArcherShootOrderWhenUnitIsArcherAndCommandIsShoot() {
        Order result = orderChooser.choose(ARCHER, SHOOT);

        assertEquals(archerShootOrder, result);
    }

    @Test
    void shouldReturnCannonShootOrderWhenUnitIsCannonAndCommandIsShoot() {
        Order result = orderChooser.choose(CANNON, SHOOT);

        assertEquals(cannonShootOrder, result);
    }

    @Test
    void shouldThrowCommandExceptionWhenCommandDoesNotExist() {
        assertThrows(CommandException.class, () -> orderChooser.choose(CANNON, MOVE));
    }
}