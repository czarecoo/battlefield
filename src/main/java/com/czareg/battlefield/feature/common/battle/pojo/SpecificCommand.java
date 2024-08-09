package com.czareg.battlefield.feature.common.battle.pojo;

import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.Value;

import java.util.List;

@Value
public class SpecificCommand {

    Unit unit;
    CommandType command;
    List<CommandDetails> details;
}
