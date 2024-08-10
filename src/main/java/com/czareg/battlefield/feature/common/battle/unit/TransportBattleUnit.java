package com.czareg.battlefield.feature.common.battle.unit;

import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.command.MoveFromOneToThreeSquaresVerticallyOrHorizontallyCommand;
import com.czareg.battlefield.feature.common.enums.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.czareg.battlefield.feature.common.enums.UnitType.TRANSPORT;

@Component
@RequiredArgsConstructor
public class TransportBattleUnit implements BattleUnit {

    private final MoveFromOneToThreeSquaresVerticallyOrHorizontallyCommand moveCommand;

    @Override
    public UnitType getUnitType() {
        return TRANSPORT;
    }

    @Override
    public List<BattleCommand> getBattleCommands() {
        return List.of(moveCommand);
    }
}
