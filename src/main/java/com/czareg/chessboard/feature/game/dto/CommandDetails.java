package com.czareg.chessboard.feature.game.dto;

import lombok.Value;

@Value
public class CommandDetails {

    Direction direction;
    int squares;
}
