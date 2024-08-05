package com.czareg.battlefield.feature.game.dto.response;

import com.czareg.battlefield.feature.common.enums.Color;
import com.czareg.battlefield.feature.common.enums.Status;
import com.czareg.battlefield.feature.common.enums.UnitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {

    private Long id;
    private PositionDTO position;
    private UnitType type;
    private Color color;
    private Status status;
}
