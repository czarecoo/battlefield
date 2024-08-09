package com.czareg.battlefield.feature.common.battle.executor;

import com.czareg.battlefield.feature.command.entity.Command;

public sealed interface ExecutionResult {

    record Success(Command command) implements ExecutionResult {
    }

    record Failure(String message) implements ExecutionResult {
    }
}
