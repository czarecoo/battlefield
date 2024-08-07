package com.czareg.battlefield.feature.common.battle.executor.component;

import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Direction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PathCalculator {

    public List<Position> calculate(Position currentPosition, CommandDetails detail) {
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
