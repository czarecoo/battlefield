package com.czareg.battlefield.feature.game.component;

import com.czareg.battlefield.config.NewGameConfig;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.game.GameRepository;
import com.czareg.battlefield.feature.game.entity.Game;
import com.czareg.battlefield.feature.unit.component.UnitFactory;
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
        int boardWidth = newGameConfig.getBoardWidth();
        int boardHeight = newGameConfig.getBoardHeight();
        Board board = new Board(boardWidth, boardHeight);
        game.setBoard(board);
        gameRepository.save(game);

        List<Unit> units = unitFactory.createUnits(game);
        game.setUnits(units);
        game.setCreatedAt(Instant.now());
    }
}
