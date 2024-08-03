package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.feature.unit.entity.Position;
import lombok.Value;

@Value
public class Command {

    CommandType commandType;
    Position targetPosition;
}
