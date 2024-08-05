package com.czareg.battlefield.feature.unit.components;

import com.czareg.battlefield.config.NewGameConfig;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.czareg.battlefield.feature.common.enums.Color.BLACK;
import static com.czareg.battlefield.feature.common.enums.Color.WHITE;

@Component
@RequiredArgsConstructor
public class PositionFactory {

    private final NewGameConfig newGameConfig;

    public Map<Color, List<Position>> createAvailable() {
        List<Position> availableWhitePositions = new ArrayList<>();
        List<Position> availableBlackPositions = new ArrayList<>();

        int boardWidth = newGameConfig.getBoardWidth();
        int boardHeight = newGameConfig.getBoardHeight();

        int position = 1;
        int allPositions = boardWidth * boardHeight;
        for (int y = 1; y <= boardHeight; y++) {
            for (int x = 1; x <= boardWidth; x++) {
                Position availablePosition = new Position(x, y);
                if (allPositions % 2 == 0) {
                    if (position <= allPositions / 2) {
                        availableWhitePositions.add(availablePosition);
                    } else {
                        availableBlackPositions.add(availablePosition);
                    }
                } else {
                    if (position <= allPositions / 2) {
                        availableWhitePositions.add(availablePosition);
                    } else if (position > allPositions / 2 + 1) {
                        availableBlackPositions.add(availablePosition);
                    }
                }
                position++;
            }
        }

        Collections.shuffle(availableWhitePositions);
        Collections.shuffle(availableBlackPositions);

        return Map.of(WHITE, availableWhitePositions, BLACK, availableBlackPositions);
    }
}
