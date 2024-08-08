package com.czareg.battlefield.feature.common.battle;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.command.*;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.unit.entity.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.CommandType.MOVE;
import static com.czareg.battlefield.feature.common.enums.CommandType.SHOOT;
import static com.czareg.battlefield.feature.common.enums.UnitType.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
class BattleCommandMatcherIT {

    @Autowired
    private BattleCommandMatcher battleCommandMatcher;

    @Test
    void shouldReturnMoveOneSquareVerticallyOrHorizontallyCommandWhenUnitIsArcherAndCommandIsMove() {
        Unit unit = new Unit();
        unit.setType(ARCHER);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of());

        BattleCommand battleCommand = battleCommandMatcher.match(specificCommand);

        assertInstanceOf(MoveOneSquareVerticallyOrHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldReturnMoveFromOneToThreeSquareVerticallyOrHorizontallyCommandWhenUnitIsTransportAndCommandIsMove() {
        Unit unit = new Unit();
        unit.setType(TRANSPORT);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of());

        BattleCommand battleCommand = battleCommandMatcher.match(specificCommand);

        assertInstanceOf(MoveFromOneToThreeSquareVerticallyOrHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldReturnShootNSquaresVerticallyOrHorizontallyCommandWhenUnitIsArcherAndCommandIsShoot() {
        Unit unit = new Unit();
        unit.setType(ARCHER);
        SpecificCommand specificCommand = new SpecificCommand(unit, SHOOT, List.of());

        BattleCommand battleCommand = battleCommandMatcher.match(specificCommand);

        assertInstanceOf(ShootNSquaresVerticallyOrHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldReturnShootNSquaresVerticallyAndNSquaresHorizontallyCommandWhenUnitIsCannonAndCommandIsShoot() {
        Unit unit = new Unit();
        unit.setType(CANNON);
        SpecificCommand specificCommand = new SpecificCommand(unit, SHOOT, List.of());

        BattleCommand battleCommand = battleCommandMatcher.match(specificCommand);

        assertInstanceOf(ShootNSquaresVerticallyAndNSquaresHorizontallyCommand.class, battleCommand);
    }

    @Test
    void shouldThrowCommandExceptionWhenCommandDoesNotExist() {
        Unit unit = new Unit();
        unit.setType(CANNON);
        SpecificCommand specificCommand = new SpecificCommand(unit, MOVE, List.of());

        assertThrows(CommandException.class, () -> battleCommandMatcher.match(specificCommand));
    }
}