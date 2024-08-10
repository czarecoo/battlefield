package com.czareg.battlefield.feature.common.battle.command;

import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.validator.SpecificCommandValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;

public interface BattleCommand {

    BattleCommandExecutor getBattleCommandExecutor();

    SpecificCommandValidator getSpecificCommandValidator();

    SpecificCommandGenerator getSpecificCommandGenerator();

    CommandType getType();
}
