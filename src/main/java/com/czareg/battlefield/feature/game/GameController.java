package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.game.dto.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.UnitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/game")
    public Game createGame() {
        return gameService.createGame();
    }

    @GetMapping("/units")
    public List<UnitDTO> listUnits() {
        return gameService.listUnits();
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
