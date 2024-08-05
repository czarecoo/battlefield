package com.czareg.battlefield.feature.game.components;

import com.czareg.battlefield.config.NewGameConfig;
import com.czareg.battlefield.feature.game.GameRepository;
import com.czareg.battlefield.feature.game.entity.Board;
import com.czareg.battlefield.feature.game.entity.Game;
import com.czareg.battlefield.feature.unit.components.UnitFactory;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GameFactory {

    private final UnitFactory unitFactory;
    private final GameRepository gameRepository;
    private final NewGameConfig newGameConfig;

    public void createGame() {
        Game game = new Game();
        Board board = new Board();
        board.setWidth(newGameConfig.getBoardWidth());
        board.setHeight(newGameConfig.getBoardHeight());
        game.setBoard(board);
        gameRepository.save(game);

        List<Unit> units = unitFactory.createUnits(game);
        game.setUnits(units);
        game.setStarted(Instant.now());
        gameRepository.save(game);
    }
}
