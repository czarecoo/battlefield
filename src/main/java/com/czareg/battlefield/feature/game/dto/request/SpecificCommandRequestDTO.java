package com.czareg.battlefield.feature.game.dto.request;

import com.czareg.battlefield.feature.command.entity.CommandType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

@Value
public class SpecificCommandRequestDTO {

    @NotNull
    Long unitId;
    @NotNull
    CommandType command;
    @NotNull
    @NotEmpty
    List<@NotNull CommandDetailsDTO> details;
}
