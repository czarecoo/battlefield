package com.czareg.battlefield.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "cooldown")
@Getter
@RequiredArgsConstructor
@ToString
public class CooldownConfig {

    private static final int TEN_MINUTES_IN_MILLIS = 600_000;

    @Min(0)
    @Max(TEN_MINUTES_IN_MILLIS)
    private final int archerMove;
    @Min(0)
    @Max(TEN_MINUTES_IN_MILLIS)
    private final int archerShot;
    @Min(0)
    @Max(TEN_MINUTES_IN_MILLIS)
    private final int transportMove;
    @Min(0)
    @Max(TEN_MINUTES_IN_MILLIS)
    private final int cannonShot;
}
