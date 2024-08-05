package com.czareg.battlefield.feature.command.order.utils;

import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TargetPositionCalculator {

    public Position calculate(Position currentPosition, List<CommandDetailsDTO> details) {
        Position target = currentPosition;
        for (CommandDetailsDTO detail : details) {
            Direction direction = detail.getDirection();
            int squares = detail.getSquares();
            target = target.calculateNewPosition(direction, squares);
        }
        return target;
    }
}
