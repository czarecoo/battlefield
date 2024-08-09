package com.czareg.battlefield.feature.common.battle.command;

import com.czareg.battlefield.config.advice.CommandException;
import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.executor.ExecutionResult;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.battle.validator.CommandDetailsValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;

import java.util.Optional;

public interface BattleCommand {

    BattleCommandExecutor getBattleCommandExecutor();

    CommandDetailsValidator getCommandDetailsValidator();

    SpecificCommandGenerator getSpecificCommandGenerator();

    CommandType getType();

    default Command validateAndExecuteOrThrow(SpecificCommand specificCommand) {
        getCommandDetailsValidator().validate(specificCommand.getDetails());
        ExecutionResult executionResult = getBattleCommandExecutor().execute(specificCommand);
        return switch (executionResult) {
            case ExecutionResult.Success(Command command) -> command;
            case ExecutionResult.Failure(String message) -> throw new CommandException(message);
        };
    }

    default Optional<Command> tryToValidateAndExecute(SpecificCommand specificCommand) {
        getCommandDetailsValidator().validate(specificCommand.getDetails());
        ExecutionResult executionResult = getBattleCommandExecutor().execute(specificCommand);
        return switch (executionResult) {
            case ExecutionResult.Success(Command command) -> Optional.of(command);
            case ExecutionResult.Failure(String unused) -> Optional.empty();
        };
    }
}
