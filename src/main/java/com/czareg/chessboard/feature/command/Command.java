package com.czareg.chessboard.feature.command;

import com.czareg.chessboard.feature.unit.Position;
import lombok.Value;

@Value
public class Command {

    CommandType commandType;
    Position targetPosition;
}
