package com.czareg.battlefield.feature.command.dto.request;

import com.czareg.battlefield.config.validation.order.*;
import com.czareg.battlefield.feature.command.validation.ExistsByUnitId;
import com.czareg.battlefield.feature.command.validation.IsActive;
import com.czareg.battlefield.feature.command.validation.IsFromCurrentGame;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@GroupSequence({RandomCommandRequestDTO.class, First.class, Second.class, Third.class, Fourth.class, Fifth.class})
public class RandomCommandRequestDTO {

    @NotNull(groups = First.class)
    @Min(value = 1, groups = Second.class)
    @ExistsByUnitId(groups = Third.class)
    @IsActive(groups = Fourth.class)
    @IsFromCurrentGame(groups = Fifth.class)
    Long unitId;
}
