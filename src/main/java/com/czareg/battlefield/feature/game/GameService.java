package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.game.components.GameFactory;
import com.czareg.battlefield.feature.game.components.GamePrinter;
import com.czareg.battlefield.feature.game.dto.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.game.entity.Game;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameFactory gameFactory;
    private final GameRepository gameRepository;
    private final GamePrinter gamePrinter;

    public void createGame() {
        gameFactory.createGame();
    }

    public Game getGame() {
        return gameRepository.getFirstByOrderByIdDesc();
    }

    public String printGame() {
        Game game = getGame();
        return gamePrinter.print(game);
    }

    public void executeSpecificCommand(SpecificCommandRequestDTO specificCommandDTO) {

    }

    public void executeRandomCommand(RandomCommandRequestDTO randomCommandDTO) {

    }

    public boolean isGameRepositoryEmpty() {
        return gameRepository.count() == 0;
    }
}
