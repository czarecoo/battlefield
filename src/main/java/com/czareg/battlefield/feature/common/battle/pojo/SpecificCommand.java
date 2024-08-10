package com.czareg.battlefield.feature.common.battle.pojo;

import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class SpecificCommand {

    Unit unit;
    CommandType command;
    List<CommandDetails> details;
    Position target;
}
