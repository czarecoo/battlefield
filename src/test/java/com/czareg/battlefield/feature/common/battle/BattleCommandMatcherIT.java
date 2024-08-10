package com.czareg.battlefield.feature.common.battle;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.command.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.UnitType.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class BattleCommandMatcherIT {

    @Autowired
    BattleCommandMatcher battleCommandMatcher;

    @Test
    void shouldReturnMoveOneSquareVerticallyOrHorizontallyCommandWhenUnitIsArcherAndCommandIsMove() {
        BattleCommand battleCommand = battleCommandMatcher.match(ARCHER, MOVE);

        assertInstanceOf(MoveOneSquareVerticallyOrHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldReturnMoveFromOneToThreeSquaresVerticallyOrHorizontallyCommandWhenUnitIsTransportAndCommandIsMove() {
        BattleCommand battleCommand = battleCommandMatcher.match(TRANSPORT, MOVE);

        assertInstanceOf(MoveFromOneToThreeSquaresVerticallyOrHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldReturnShootNSquaresVerticallyOrHorizontallyCommandWhenUnitIsArcherAndCommandIsShoot() {
        BattleCommand battleCommand = battleCommandMatcher.match(ARCHER, SHOOT);

        assertInstanceOf(ShootNSquaresVerticallyOrHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldReturnShootNSquaresVerticallyAndNSquaresHorizontallyCommandWhenUnitIsCannonAndCommandIsShoot() {
        BattleCommand battleCommand = battleCommandMatcher.match(CANNON, SHOOT);

        assertInstanceOf(ShootNSquaresVerticallyAndNSquaresHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldThrowCommandExceptionWhenCommandDoesNotExist() {
        assertThrows(CommandException.class, () -> battleCommandMatcher.match(CANNON, MOVE));
    }
}