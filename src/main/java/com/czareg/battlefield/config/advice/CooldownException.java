package com.czareg.battlefield.config.advice;

import java.time.Duration;

public class CooldownException extends RuntimeException {

    private static final String COOLDOWN_LEFT_TEMPLATE = "Unit is still in cooldown for another: %d ms";

    public CooldownException(Duration cooldownLeft) {
        super(COOLDOWN_LEFT_TEMPLATE.formatted(cooldownLeft.toMillis()));
    }
}
