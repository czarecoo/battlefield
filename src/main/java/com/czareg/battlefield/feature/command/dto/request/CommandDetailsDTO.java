package com.czareg.battlefield.feature.command.dto.request;

import com.czareg.battlefield.config.validation.order.First;
import com.czareg.battlefield.config.validation.order.Second;
import com.czareg.battlefield.config.validation.order.Third;
import com.czareg.battlefield.feature.command.validation.HasEnumValue;
import com.czareg.battlefield.feature.common.enums.Direction;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
@GroupSequence({CommandDetailsDTO.class, First.class, Second.class, Third.class})
public class CommandDetailsDTO {

    @NotNull(groups = First.class)
    @NotEmpty(groups = Second.class)
    @HasEnumValue(enumClass = Direction.class, groups = Third.class)
    String direction;

    @NotNull(groups = First.class)
    @Min(value = 1, groups = Second.class)
    Integer squares;
}
