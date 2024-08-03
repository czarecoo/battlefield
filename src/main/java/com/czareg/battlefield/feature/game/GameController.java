package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.game.dto.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.game.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/game")
    public void createGame() {
        gameService.createGame();
    }

    @GetMapping("/units")
    public Game listUnits() {
        Game game = gameService.listUnits();
        print(game);
        return game;
    }

    private static void print(Game game) {
        int width = game.getBoard().getWidth();
        int height = game.getBoard().getHeight();

        // Initialize the board array with dynamic dimensions
        String[][] array = new String[width][height];

        // Fill the array with unit information
        game.getUnits().forEach(unit -> {
            int x = unit.getPosition().getX();
            int y = unit.getPosition().getY();
            array[x][y] = "" + unit.getColor().toString().charAt(0) + unit.getType().toString().charAt(0);
        });

        // Print the board to the console
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String cell = array[x][y];
                if (cell == null) {
                    System.out.print("[  ] "); // Empty cell
                } else {
                    System.out.print("[" + cell + "] "); // Filled cell
                }
            }
            System.out.println(); // New line after each row
        }
    }

    @PostMapping("/commands/specific")
    public void executeSpecificCommand(@RequestBody SpecificCommandRequestDTO specificCommandDTO) {
        gameService.executeSpecificCommand(specificCommandDTO);
    }

    @PostMapping("/commands/random")
    public void executeRandomCommand(@RequestBody RandomCommandRequestDTO randomCommandDTO) {
        gameService.executeRandomCommand(randomCommandDTO);
    }
}
