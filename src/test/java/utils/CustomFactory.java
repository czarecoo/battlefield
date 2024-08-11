package utils;

import com.czareg.battlefield.feature.common.battle.command.BattleCommand;
import com.czareg.battlefield.feature.common.battle.executor.BattleCommandExecutor;
import com.czareg.battlefield.feature.common.battle.generator.SpecificCommandGenerator;
import com.czareg.battlefield.feature.common.battle.validator.SpecificCommandValidator;
import com.czareg.battlefield.feature.common.enums.CommandType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomFactory {

    public static BattleCommand create(SpecificCommandValidator validator, BattleCommandExecutor executor) {
        return new BattleCommand() {

            @Override
            public BattleCommandExecutor getBattleCommandExecutor() {
                return executor;
            }

            @Override
            public SpecificCommandValidator getSpecificCommandValidator() {
                return validator;
            }

            @Override
            public SpecificCommandGenerator getSpecificCommandGenerator() {
                return null;
            }

            @Override
            public CommandType getType() {
                return null;
            }
        };
    }
}
