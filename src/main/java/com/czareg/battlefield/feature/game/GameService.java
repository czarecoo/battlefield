package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.common.enums.Color;
import com.czareg.battlefield.feature.game.component.GameFactory;
import com.czareg.battlefield.feature.game.component.GamePrinter;
import com.czareg.battlefield.feature.game.dto.response.GameDTO;
import com.czareg.battlefield.feature.game.dto.response.UnitDTO;
import com.czareg.battlefield.feature.game.entity.Game;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameFactory gameFactory;
    private final GameRepository gameRepository;
    private final GamePrinter gamePrinter;

    @Transactional
    public void createGame() {
        gameFactory.createGame();
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public String printGame() {
        Game game = gameRepository.getFirstByOrderByIdDesc();
        return gamePrinter.print(game);
    }

    public boolean isGameRepositoryEmpty() {
        return gameRepository.count() == 0;
    }
}
