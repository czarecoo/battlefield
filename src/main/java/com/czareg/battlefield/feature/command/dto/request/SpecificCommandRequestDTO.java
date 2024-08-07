package com.czareg.battlefield.feature.command.dto.request;

import com.czareg.battlefield.feature.command.validation.ExistsByUnitId;
import com.czareg.battlefield.feature.command.validation.HasEnumValue;
import com.czareg.battlefield.feature.command.validation.IsActive;
import com.czareg.battlefield.feature.common.enums.CommandType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

@Value
public class SpecificCommandRequestDTO {

    @NotNull
    @Min(1)
    @ExistsByUnitId
    @IsActive
    Long unitId;

    @NotNull
    @NotEmpty
    @HasEnumValue(enumClass = CommandType.class)
    String command;

    @NotNull
    @NotEmpty
    @Valid
    List<@NotNull CommandDetailsDTO> details;
}
