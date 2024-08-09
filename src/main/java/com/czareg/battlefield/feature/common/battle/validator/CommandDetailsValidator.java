package com.czareg.battlefield.feature.common.battle.validator;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;

import java.util.List;

public interface CommandDetailsValidator {

    void validate(List<CommandDetails> details);
}
