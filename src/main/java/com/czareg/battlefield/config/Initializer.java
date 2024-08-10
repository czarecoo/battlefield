package com.czareg.battlefield.config;

import com.czareg.battlefield.feature.game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Initializer implements ApplicationRunner {

    private final NewGameConfig newGameConfig;
    private final CooldownConfig cooldownConfig;
    private final GameService gameService;

    @Override
    public void run(ApplicationArguments args) {
        log.info(newGameConfig.toString());
        log.info(cooldownConfig.toString());
        if (gameService.isGameRepositoryEmpty()) {
            log.info("No game found in repository. Creating new game.");
            gameService.createGame();
        }
    }
}
