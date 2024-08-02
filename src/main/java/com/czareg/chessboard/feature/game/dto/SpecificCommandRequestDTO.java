package com.czareg.chessboard.feature.game.dto;

import com.czareg.chessboard.feature.command.CommandType;
import lombok.Value;

import java.util.List;

@Value
public class SpecificCommandRequestDTO {

    String unitId;
    CommandType commandType;
    List<CommandDetails> commandDetails;
}
