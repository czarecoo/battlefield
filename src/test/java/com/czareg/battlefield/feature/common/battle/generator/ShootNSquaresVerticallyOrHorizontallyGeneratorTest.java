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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShootNSquaresVerticallyOrHorizontallyGeneratorTest {

    @Mock
    private SpecificCommandFactory specificCommandFactory;

    @InjectMocks
    private ShootNSquaresVerticallyOrHorizontallyGenerator generator;

    @Test
    void shouldGenerateCorrectNumberOfSpecificCommandsFor5x4Board() {
        Unit unit = Unit.builder()
                .game(Game.builder()
                        .board(new Board(5, 4))
                        .build())
                .build();
        when(specificCommandFactory.create(any(), any(), anyList())).thenReturn(SpecificCommand.builder().build());

        List<SpecificCommand> specificCommands = generator.generate(unit);

        // 4 squares max * 2 directions + 3 squares max * 2 directions
        assertEquals(14, specificCommands.size());
    }

    @Test
    void shouldGenerateCorrectNumberOfSpecificCommandsFor10x10Board() {
        Unit unit = Unit.builder()
                .game(Game.builder()
                        .board(new Board(10, 10))
                        .build())
                .build();
        when(specificCommandFactory.create(any(), any(), anyList())).thenReturn(SpecificCommand.builder().build());

        List<SpecificCommand> specificCommands = generator.generate(unit);
        // 9 squares max * 4 directions
        assertEquals(36, specificCommands.size());
    }
}
