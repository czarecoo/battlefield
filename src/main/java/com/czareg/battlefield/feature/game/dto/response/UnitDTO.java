package com.czareg.battlefield.feature.game.dto.response;

import com.czareg.battlefield.feature.unit.entity.Color;
import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Status;
import com.czareg.battlefield.feature.unit.entity.UnitType;
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
    private Position position;
    private UnitType type;
    private Color color;
    private Status status;
}
