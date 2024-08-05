package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.common.enums.Color;
import com.czareg.battlefield.feature.game.dto.response.GameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createGame() {
        gameService.createGame();
    }

    @GetMapping
    public GameDTO getGame(@RequestParam(value = "color", required = false) Color color) {
        return gameService.getGame(color);
    }

    @GetMapping("/print")
    public String printGame() {
        return gameService.printGame();
    }
}
