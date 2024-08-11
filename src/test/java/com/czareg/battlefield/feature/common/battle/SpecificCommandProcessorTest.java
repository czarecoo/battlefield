package com.czareg.battlefield.feature.common.battle;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.executor.ExecutionResult;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.unit.entity.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.CustomFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecificCommandProcessorTest {

    static final SpecificCommand SPECIFIC_COMMAND = SpecificCommand.builder()
            .unit(new Unit())
            .build();

    @Mock
    BattleCommandMatcher battleCommandMatcher;

    @InjectMocks
    SpecificCommandProcessor specificCommandProcessor;

    @Test
    void shouldProcessAndReturnCommandSuccessfully() {
        Command expectedCommand = new Command();
        BattleCommand battleCommand = CustomFactory.create(
                specificCommand -> Optional.empty(),
                specificCommand -> new ExecutionResult.Success(expectedCommand)
        );
        when(battleCommandMatcher.match(any(), any())).thenReturn(battleCommand);

        Command resultCommand = specificCommandProcessor.processOrThrow(SPECIFIC_COMMAND);

        assertEquals(expectedCommand, resultCommand);
    }

    @Test
    void shouldThrowCommandExceptionWhenValidationFails() {
        CommandException expectedException = new CommandException("Validation failed");
        BattleCommand battleCommand = CustomFactory.create(
                specificCommand -> Optional.of(expectedException),
                specificCommand -> null
        );
        when(battleCommandMatcher.match(any(), any())).thenReturn(battleCommand);

        assertThrows(CommandException.class, () -> specificCommandProcessor.processOrThrow(SPECIFIC_COMMAND));
    }

    @Test
    void shouldThrowCommandExceptionWhenExecutionFails() {
        BattleCommand battleCommand = CustomFactory.create(
                specificCommand -> Optional.empty(),
                specificCommand -> new ExecutionResult.Failure("Execution failed")
        );

        when(battleCommandMatcher.match(any(), any())).thenReturn(battleCommand);

        assertThrows(CommandException.class, () -> specificCommandProcessor.processOrThrow(SPECIFIC_COMMAND));
    }

    @Test
    void shouldReturnEmptyOptionalWhenValidationFailsInProcessOrEmpty() {
        CommandException expectedException = new CommandException("Validation failed");
        BattleCommand battleCommand = CustomFactory.create(
                specificCommand -> Optional.of(expectedException),
                specificCommand -> null
        );
        when(battleCommandMatcher.match(any(), any())).thenReturn(battleCommand);

        Optional<Command> result = specificCommandProcessor.processOrEmpty(SPECIFIC_COMMAND);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnCommandInProcessOrEmptyWhenExecutionIsSuccessful() {
        Command expectedCommand = new Command();
        BattleCommand battleCommand = CustomFactory.create(
                specificCommand -> Optional.empty(),
                specificCommand -> new ExecutionResult.Success(expectedCommand)
        );
        when(battleCommandMatcher.match(any(), any())).thenReturn(battleCommand);

        Optional<Command> result = specificCommandProcessor.processOrEmpty(SPECIFIC_COMMAND);

        assertTrue(result.isPresent());
        assertEquals(expectedCommand, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalInProcessOrEmptyWhenExecutionFails() {
        BattleCommand battleCommand = CustomFactory.create(
                specificCommand -> Optional.empty(),
                specificCommand -> new ExecutionResult.Failure("Execution failed")
        );
        when(battleCommandMatcher.match(any(), any())).thenReturn(battleCommand);

        Optional<Command> result = specificCommandProcessor.processOrEmpty(SPECIFIC_COMMAND);

        assertTrue(result.isEmpty());
    }
}
