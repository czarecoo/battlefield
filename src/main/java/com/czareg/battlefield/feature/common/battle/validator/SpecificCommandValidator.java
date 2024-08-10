package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;

import java.util.Optional;

public interface SpecificCommandValidator {

    Optional<CommandException> validate(SpecificCommand specificCommand);
}
