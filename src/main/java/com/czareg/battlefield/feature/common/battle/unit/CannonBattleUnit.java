package com.czareg.battlefield.feature.common.battle.unit;

import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.command.ShootNSquaresVerticallyOrHorizontallyCommand;
import com.czareg.battlefield.feature.common.enums.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.UnitType.CANNON;

@Component
@RequiredArgsConstructor
public class CannonBattleUnit implements BattleUnit {

    private final ShootNSquaresVerticallyOrHorizontallyCommand moveCommand;

    @Override
    public UnitType getUnitType() {
        return CANNON;
    }

    @Override
    public List<BattleCommand> getBattleCommands() {
        return List.of(moveCommand);
    }
}
