package com.czareg.battlefield.feature.game.entity;

import com.czareg.battlefield.feature.game.dto.response.BoardDTO;
import com.czareg.battlefield.feature.unit.entity.Position;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private static final int LOWEST_VALID_X = 1;
    private static final int LOWEST_VALID_Y = 1;

    private int width;
    private int height;

    public boolean isInvalid(Position target) {
        int x = target.getX();
        int y = target.getY();
        return x < LOWEST_VALID_X || x > width || y < LOWEST_VALID_Y || y > height;
    }

    public BoardDTO toDTO() {
        return BoardDTO.builder()
                .width(width)
                .height(height)
                .build();
    }
}
