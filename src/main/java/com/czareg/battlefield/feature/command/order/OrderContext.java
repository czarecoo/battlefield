package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.Value;

@Value
public class OrderContext {

    Unit unit;
    SpecificCommandRequestDTO specificCommandDTO;
}
