package com.czareg.chessboard.feature.game.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public class PositionDTO {

    int x;
    int y;
}
