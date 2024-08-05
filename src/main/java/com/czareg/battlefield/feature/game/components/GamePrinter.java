package com.czareg.battlefield.feature.game.components;

import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.game.entity.Game;
import org.springframework.stereotype.Component;

import static com.czareg.battlefield.feature.common.enums.Status.DESTROYED;

@Component
public class GamePrinter {

    public String print(Game game) {
        Board board = game.getBoard();
        int width = board.getWidth();
        int height = board.getHeight();

        String[][] array = new String[width][height];

        game.getUnits().forEach(unit -> {
            if (unit.getStatus() == DESTROYED) {
                return;
            }
            int x = unit.getPosition().getX();
            int y = unit.getPosition().getY();
            array[x - 1][y - 1] = "%c%c%03d".formatted(unit.getColor().toString().charAt(0), unit.getType().toString().charAt(0), unit.getId());
        });

        StringBuilder stringBuilder = new StringBuilder();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                String cell = array[x][y];
                if (cell == null) {
                    stringBuilder.append("[     ] ");
                } else {
                    stringBuilder.append("[").append(cell).append("] ");
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
