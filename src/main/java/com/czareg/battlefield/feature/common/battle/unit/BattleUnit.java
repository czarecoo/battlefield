package com.czareg.battlefield.feature.common.battle.unit;

import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.enums.UnitType;

import java.util.List;

public interface BattleUnit {

    UnitType getUnitType();

    List<BattleCommand> getBattleCommands();
}
