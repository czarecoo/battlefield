package com.czareg.battlefield.feature.command.components;

import com.czareg.battlefield.config.CooldownConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class CooldownChecker {

    private final CooldownConfig config;

    public boolean canArcherMove(Instant lastCommandTime){
        return isOffCooldown(lastCommandTime, config.getArcherMove());
    }

    public boolean canArcherShoot(Instant lastCommandTime){
        return isOffCooldown(lastCommandTime, config.getArcherShot());
    }

    public boolean canTransportMove(Instant lastCommandTime){
        return isOffCooldown(lastCommandTime, config.getTransportMove());
    }

    public boolean canCannonShoot(Instant lastCommandTime){
        return isOffCooldown(lastCommandTime, config.getCannonShot());
    }

    private boolean isOffCooldown(Instant lastCommandTime, int cooldownTime){
        return Duration.between(lastCommandTime, Instant.now()).toMillis() <= cooldownTime;
    }
}
