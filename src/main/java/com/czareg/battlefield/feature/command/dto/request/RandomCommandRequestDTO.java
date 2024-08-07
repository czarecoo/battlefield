package com.czareg.battlefield.feature.command.dto.request;

import com.czareg.battlefield.feature.command.validation.ExistsByUnitId;
import com.czareg.battlefield.feature.command.validation.IsActive;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class RandomCommandRequestDTO {

    @NotNull
    @Min(1)
    @ExistsByUnitId
    @IsActive
    Long unitId;
}
