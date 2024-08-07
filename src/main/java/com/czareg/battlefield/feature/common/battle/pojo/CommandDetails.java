package com.czareg.battlefield.feature.common.battle.pojo;

import com.czareg.battlefield.feature.common.enums.Direction;
import lombok.Value;

@Value
public class CommandDetails {

    Direction direction;
    int squares;
}
