package com.czareg.battlefield.feature.command.component;

import com.czareg.battlefield.config.advice.CooldownException;
import com.czareg.battlefield.feature.command.CommandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CooldownCheckerTest {

    @Mock
    private CommandRepository commandRepository;

    @InjectMocks
    private CooldownChecker cooldownChecker;

    @Test
    void shouldThrowCooldownExceptionWhenCurrentTimeIsBeforeCooldownFinishingTime() {
        Instant now = Instant.now();
        Instant cooldownFinishingAt = now.plus(Duration.ofMinutes(5));

        when(commandRepository.getLastCooldownFinishingTimeForUnitId(any(), any())).thenReturn(Optional.of(cooldownFinishingAt));

        assertThrows(CooldownException.class, () -> cooldownChecker.check(1L));
    }

    @Test
    void shouldNotThrowExceptionWhenCurrentTimeIsAfterCooldownFinishingTime() {
        Instant cooldownFinishingAt = Instant.now().minus(Duration.ofMinutes(5));
        when(commandRepository.getLastCooldownFinishingTimeForUnitId(any(), any())).thenReturn(Optional.of(cooldownFinishingAt));

        assertDoesNotThrow(() -> cooldownChecker.check(1L));
    }

    @Test
    void shouldNotThrowExceptionWhenNoCooldownFinishingTimeIsPresent() {
        when(commandRepository.getLastCooldownFinishingTimeForUnitId(any(), any())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> cooldownChecker.check(1L));
    }
}
