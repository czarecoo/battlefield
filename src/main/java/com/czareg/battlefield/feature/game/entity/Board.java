package com.czareg.battlefield.feature.game.entity;

import com.czareg.battlefield.feature.unit.entity.Position;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {

    private int width;
    private int height;

    public boolean isInvalid(Position target) {
        int x = target.getX();
        int y = target.getY();
        return x < 1 || x > width || y < 1 || y > height;
    }
}
