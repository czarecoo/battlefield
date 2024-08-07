package com.czareg.battlefield.feature.command.components;

import com.czareg.battlefield.config.advice.exceptions.CooldownException;
import com.czareg.battlefield.feature.command.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CooldownChecker {

    private final CommandRepository commandRepository;

    public void check(Long unitId) {
        commandRepository.findFirstByUnitIdOrderByIdDesc(unitId).ifPresent(lastCommand -> {
            Instant cooldownFinishingAt = lastCommand.getCooldownFinishingAt();
            Instant now = Instant.now();
            if (now.isBefore(cooldownFinishingAt)) {
                throw new CooldownException(Duration.between(now, cooldownFinishingAt));
            }
        });
    }
}
