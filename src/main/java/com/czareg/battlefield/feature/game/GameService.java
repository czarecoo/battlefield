package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.config.NewGameConfig;
import com.czareg.battlefield.feature.game.dto.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.UnitDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final NewGameConfig newGameConfig;

    public Game createGame() {
        return null;
    }

    public List<UnitDTO> listUnits() {
        Optional<Game> game = gameRepository.findFirstByOrderByIdDesc();
        return null;
    }

    public void executeSpecificCommand(SpecificCommandRequestDTO specificCommandDTO) {

    }

    public void executeRandomCommand(RandomCommandRequestDTO randomCommandDTO) {

    }

    public boolean isGameRepositoryEmpty() {
        return gameRepository.count() == 0;
    }
}
