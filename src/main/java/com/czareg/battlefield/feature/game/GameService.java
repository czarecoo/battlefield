package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.game.components.GameFactory;
import com.czareg.battlefield.feature.game.components.GamePrinter;
import com.czareg.battlefield.feature.game.dto.response.GameDTO;
import com.czareg.battlefield.feature.game.dto.response.UnitDTO;
import com.czareg.battlefield.feature.game.entity.Game;
import com.czareg.battlefield.feature.unit.entity.Color;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

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

    @Transactional(readOnly = true, isolation = REPEATABLE_READ)
    public GameDTO getGame(Color color) {
        Game game = gameRepository.getFirstByOrderByIdDesc();
        GameDTO gameDTO = game.toDTO();
        if (color != null) {
            List<UnitDTO> filteredUnits = gameDTO.getUnits().stream()
                    .filter(unit -> unit.getColor() == color)
                    .toList();
            gameDTO.setUnits(filteredUnits);
        }
        return gameDTO;
    }

    @Transactional(readOnly = true, isolation = REPEATABLE_READ)
    public String printGame() {
        Game game = gameRepository.getFirstByOrderByIdDesc();
        return gamePrinter.print(game);
    }

    public boolean isGameRepositoryEmpty() {
        return gameRepository.count() == 0;
    }
}
