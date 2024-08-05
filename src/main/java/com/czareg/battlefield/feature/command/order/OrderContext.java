package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.Value;

import java.util.List;

@Value
public class OrderContext {

    Unit unit;
    List<CommandDetailsDTO> details;
}
