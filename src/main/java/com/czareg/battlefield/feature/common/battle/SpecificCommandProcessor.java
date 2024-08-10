package com.czareg.battlefield.feature.common.battle;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.executor.ExecutionResult;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpecificCommandProcessor {

    private final BattleCommandMatcher battleCommandMatcher;

    public Command processOrThrow(SpecificCommand specificCommand) {
        BattleCommand battleCommand = battleCommandMatcher.match(specificCommand.getUnit().getType(), specificCommand.getCommand());
        Optional<CommandException> optionalCommandException = battleCommand.getSpecificCommandValidator().validate(specificCommand);
        optionalCommandException.ifPresent(commandException -> {
            throw commandException;
        });
        ExecutionResult executionResult = battleCommand.getBattleCommandExecutor().execute(specificCommand);
        return switch (executionResult) {
            case ExecutionResult.Success(Command command) -> command;
            case ExecutionResult.Failure(String message) -> throw new CommandException(message);
        };
    }

    public Optional<Command> processOrEmpty(SpecificCommand specificCommand) {
        BattleCommand battleCommand = battleCommandMatcher.match(specificCommand.getUnit().getType(), specificCommand.getCommand());
        Optional<CommandException> optionalCommandException = battleCommand.getSpecificCommandValidator().validate(specificCommand);
        if (optionalCommandException.isPresent()) {
            return Optional.empty();
        }
        ExecutionResult executionResult = battleCommand.getBattleCommandExecutor().execute(specificCommand);
        return switch (executionResult) {
            case ExecutionResult.Success(Command command) -> Optional.of(command);
            case ExecutionResult.Failure(String unused) -> Optional.empty();
        };
    }
}
