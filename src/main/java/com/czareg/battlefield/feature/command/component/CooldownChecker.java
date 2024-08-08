package com.czareg.battlefield.feature.command.component;

import com.czareg.battlefield.config.advice.CooldownException;
import com.czareg.battlefield.feature.command.CommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CooldownChecker {

    private final CommandRepository commandRepository;

    public void check(Long unitId) {
        commandRepository.getLastCooldownFinishingTimeForUnitId(unitId, Limit.of(1)).ifPresent(cooldownFinishingAt -> {
            Instant now = Instant.now();
            if (now.isBefore(cooldownFinishingAt)) {
                throw new CooldownException(Duration.between(now, cooldownFinishingAt));
            }
        });
    }
}
