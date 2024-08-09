package com.czareg.battlefield.feature.common.battle.unit;

import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.command.MoveOneSquareVerticallyOrHorizontallyCommand;
import com.czareg.battlefield.feature.common.battle.command.ShootNSquaresVerticallyOrHorizontallyCommand;
import com.czareg.battlefield.feature.common.enums.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.UnitType.ARCHER;

@Component
@RequiredArgsConstructor
public class ArcherBattleUnit implements BattleUnit {

    private final MoveOneSquareVerticallyOrHorizontallyCommand moveCommand;
    private final ShootNSquaresVerticallyOrHorizontallyCommand shootCommand;

    @Override
    public UnitType getUnitType() {
        return ARCHER;
    }

    @Override
    public List<BattleCommand> getBattleCommands() {
        return List.of(moveCommand, shootCommand);
    }
}
