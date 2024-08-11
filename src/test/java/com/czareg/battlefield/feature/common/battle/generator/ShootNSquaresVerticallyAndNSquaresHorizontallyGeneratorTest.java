package com.czareg.battlefield.feature.common.battle.generator;

import com.czareg.battlefield.feature.common.battle.SpecificCommandFactory;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.game.entity.Game;
import com.czareg.battlefield.feature.unit.entity.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShootNSquaresVerticallyAndNSquaresHorizontallyGeneratorTest {

    @Mock
    private ShootNSquaresVerticallyOrHorizontallyGenerator generator;
    @Mock
    private SpecificCommandFactory specificCommandFactory;
    @InjectMocks
    private ShootNSquaresVerticallyAndNSquaresHorizontallyGenerator shootGenerator;

    @Test
    void shouldGenerateCorrectNumberOfSpecificCommandsFor3x3Board() {
        Unit unit = Unit.builder()
                .game(Game.builder()
                        .board(new Board(3, 3))
                        .build())
                .build();
        when(generator.generate(any())).thenReturn(generateSpecificCommands(8));
        when(specificCommandFactory.create(any(), any(), any())).thenReturn(SpecificCommand.builder().build());

        List<SpecificCommand> specificCommands = shootGenerator.generate(unit);

        // 8 from the base generator + 16 from diagonals (4 per direction)
        assertEquals(24, specificCommands.size());
    }

    @Test
    void shouldGenerateCorrectNumberOfSpecificCommandsFor10x10Board() {
        Unit unit = Unit.builder()
                .game(Game.builder()
                        .board(new Board(10, 10))
                        .build())
                .build();
        when(generator.generate(any())).thenReturn(generateSpecificCommands(36));
        when(specificCommandFactory.create(any(), any(), any())).thenReturn(SpecificCommand.builder().build());

        List<SpecificCommand> specificCommands = shootGenerator.generate(unit);

        // 36 from the base generator + 324 from diagonals (81 per direction)
        assertEquals(360, specificCommands.size());
    }


    private List<SpecificCommand> generateSpecificCommands(int count) {
        List<SpecificCommand> commands = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            commands.add(SpecificCommand.builder().build());
        }
        return commands;
    }
}