package com.czareg.chessboard.feature.game.dto;

import com.czareg.chessboard.feature.unit.Status;
import lombok.Value;

@Value
public class UnitDTO {

    String id;
    PositionDTO position;
    Status status;
}
