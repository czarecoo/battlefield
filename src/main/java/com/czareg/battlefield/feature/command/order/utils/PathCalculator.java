package com.czareg.battlefield.feature.command.order.utils;

import com.czareg.battlefield.feature.command.dto.request.CommandDetailsDTO;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PathCalculator {

    public List<Position> calculate(Position currentPosition, CommandDetailsDTO detail) {
        List<Position> positions = new ArrayList<>();
        Direction direction = detail.getDirection();
        int squares = detail.getSquares();
        Position target = currentPosition;
        for (int i = 1; i <= squares; i++) {
            target = target.calculateNewPosition(direction, 1);
            positions.add(target);
        }
        return positions;
    }
}
