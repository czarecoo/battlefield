package com.czareg.battlefield.feature.command.dto.request;

import com.czareg.battlefield.config.validation.order.*;
import com.czareg.battlefield.feature.command.validation.ExistsByUnitId;
import com.czareg.battlefield.feature.command.validation.HasEnumValue;
import com.czareg.battlefield.feature.command.validation.IsActive;
import com.czareg.battlefield.feature.command.validation.IsFromCurrentGame;
import com.czareg.battlefield.feature.common.enums.CommandType;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

@Value
@GroupSequence({SpecificCommandRequestDTO.class, First.class, Second.class, Third.class, Fourth.class, Fifth.class})
public class SpecificCommandRequestDTO {

    @NotNull(groups = First.class)
    @Min(value = 1, groups = Second.class)
    @ExistsByUnitId(groups = Third.class)
    @IsActive(groups = Fourth.class)
    @IsFromCurrentGame(groups = Fifth.class)
    Long unitId;

    @NotNull(groups = First.class)
    @NotEmpty(groups = Second.class)
    @HasEnumValue(enumClass = CommandType.class, groups = Third.class)
    String command;

    @NotNull(groups = First.class)
    @NotEmpty(groups = Second.class)
    @Valid
    List<@NotNull CommandDetailsDTO> details;
}
