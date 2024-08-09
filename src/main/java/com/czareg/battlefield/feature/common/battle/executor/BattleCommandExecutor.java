package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;

public interface BattleCommandExecutor {

    ExecutionResult execute(SpecificCommand specificCommand);
}
