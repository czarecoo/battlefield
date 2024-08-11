package com.czareg.battlefield.feature.common.battle.generator;

import com.czareg.battlefield.feature.common.battle.SpecificCommandFactory;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
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
class MoveOneSquareVerticallyOrHorizontallyGeneratorTest {

    @Mock
    private SpecificCommandFactory specificCommandFactory;

    @InjectMocks
    private MoveOneSquareVerticallyOrHorizontallyGenerator generator;

    @Test
    void shouldGenerateCorrectNumberOfSpecificCommands() {
        when(specificCommandFactory.create(any(), any(), anyList())).thenReturn(SpecificCommand.builder().build());

        List<SpecificCommand> specificCommands = generator.generate(null);

        assertEquals(4, specificCommands.size());
    }
}