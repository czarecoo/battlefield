package com.czareg.battlefield.feature.game.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RandomCommandRequestDTO {

    @NotNull
    Long unitId;
}
