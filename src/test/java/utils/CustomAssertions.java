package utils;

import com.czareg.battlefield.feature.command.entity.Command;
import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.experimental.UtilityClass;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UtilityClass
public class CustomAssertions {

    public static void assertCommand(Position before, Position target, Unit unit, CommandType commandType, int cooldown, Command actual) {
        assertEquals(before, actual.getBefore());
        assertEquals(target, actual.getTarget());
        assertEquals(unit, actual.getUnit());
        assertEquals(commandType, actual.getType());
        assertEquals(cooldown, actual.getCooldownFinishingAt().toEpochMilli() - actual.getCreatedAt().toEpochMilli());
    }
}
