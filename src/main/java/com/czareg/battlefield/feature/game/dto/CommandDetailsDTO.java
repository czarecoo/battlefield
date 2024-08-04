package com.czareg.battlefield.feature.game.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CommandDetailsDTO {

    @NotNull
    Direction direction;
    @NotNull
    @Min(1)
    Integer squares;
}
