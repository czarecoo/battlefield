package com.czareg.battlefield.config;

import com.czareg.battlefield.config.validation.TotalUnitsFitBoard;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "new.game")
@Getter
@RequiredArgsConstructor
@TotalUnitsFitBoard
@ToString
public class NewGameConfig {

    @Min(1)
    @Max(10)
    private final int boardWidth;
    @Min(1)
    @Max(10)
    private final int boardHeight;
    @Min(0)
    @Max(50)
    private final int archerCount;
    @Min(0)
    @Max(50)
    private final int cannonCount;
    @Min(0)
    @Max(50)
    private final int transportCount;
}