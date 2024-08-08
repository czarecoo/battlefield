package com.czareg.battlefield.feature.command.dto.request;

import com.czareg.battlefield.feature.command.validation.ExistsByUnitId;
import com.czareg.battlefield.feature.command.validation.IsActive;
import com.czareg.battlefield.feature.command.validation.IsFromCurrentGame;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RandomCommandRequestDTO {

    @NotNull
    @Min(1)
    @ExistsByUnitId
    @IsActive
    @IsFromCurrentGame
    Long unitId;
}
