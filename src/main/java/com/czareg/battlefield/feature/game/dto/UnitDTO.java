package com.czareg.battlefield.feature.game.dto;

import com.czareg.battlefield.feature.unit.entity.Status;
import lombok.Value;

@Value
public class UnitDTO {

    String id;
    PositionDTO position;
    Status status;
}
