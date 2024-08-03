package com.czareg.battlefield.feature.game.dto;

import com.czareg.battlefield.feature.command.CommandType;
import lombok.Value;

import java.util.List;

@Value
public class SpecificCommandRequestDTO {

    String unitId;
    CommandType commandType;
    List<CommandDetails> commandDetails;
}
