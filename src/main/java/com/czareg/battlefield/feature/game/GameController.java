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

    @GetMapping("/game")
    public Game getGame() {
        return gameService.getGame();
    }

    @GetMapping("/game/print")
    public String printGame() {
        return gameService.printGame();
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
