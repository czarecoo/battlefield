package com.czareg.battlefield.feature.command.component;

import com.czareg.battlefield.feature.command.dto.request.SpecificCommandRequestDTO;
import com.czareg.battlefield.feature.common.battle.SpecificCommandFactory;
import com.czareg.battlefield.feature.common.battle.pojo.CommandDetails;
import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.common.enums.Direction;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecificCommandDTOToPojoMapper {

    private final SpecificCommandFactory specificCommandFactory;

    public SpecificCommand map(SpecificCommandRequestDTO specificCommandDTO, Unit unit) {
        List<CommandDetails> commandDetails = specificCommandDTO.getDetails()
                .stream()
                .map(dto -> new CommandDetails(Direction.valueOf(dto.getDirection()), dto.getSquares()))
                .toList();
        return specificCommandFactory.create(unit, CommandType.valueOf(specificCommandDTO.getCommand()), commandDetails);
    }
}
