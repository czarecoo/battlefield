package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.game.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public void createGame() {
        gameService.createGame();
    }

    @GetMapping
    public Game getGame() {
        return gameService.getGame();
    }

    @GetMapping("/print")
    public String printGame() {
        return gameService.printGame();
    }
}
