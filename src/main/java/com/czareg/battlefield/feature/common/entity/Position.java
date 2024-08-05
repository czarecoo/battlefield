package com.czareg.battlefield.feature.common.entity;

import com.czareg.battlefield.feature.common.enums.Direction;
import com.czareg.battlefield.feature.game.dto.response.PositionDTO;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Position {

    int x;
    int y;

    public Position calculateTarget(Direction direction, int squares) {
        int newX = x;
        int newY = y;
        switch (direction) {
            case LEFT -> newX -= squares;
            case DOWN -> newY -= squares;
            case RIGHT -> newX += squares;
            case UP -> newY += squares;
        }
        return new Position(newX, newY);
    }

    public PositionDTO toDTO() {
        return PositionDTO.builder()
                .x(x)
                .y(y)
                .build();
    }
}
