package com.czareg.battlefield.feature.unit.components;

import com.czareg.battlefield.config.NewGameConfig;
import com.czareg.battlefield.feature.game.entity.Game;
import com.czareg.battlefield.feature.unit.UnitService;
import com.czareg.battlefield.feature.unit.entity.Color;
import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import com.czareg.battlefield.feature.unit.entity.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czareg.battlefield.feature.unit.entity.Color.BLACK;
import static com.czareg.battlefield.feature.unit.entity.Color.WHITE;
import static com.czareg.battlefield.feature.unit.entity.Status.ACTIVE;
import static com.czareg.battlefield.feature.unit.entity.UnitType.*;

@Component
@RequiredArgsConstructor
public class UnitFactory {

    private final NewGameConfig newGameConfig;
    private final PositionFactory positionFactory;
    private final UnitService unitService;

    public List<Unit> createUnits(Game game) {
        Map<Color, List<Position>> colorPositions = positionFactory.createAvailable();
        List<Position> availableWhitePositions = colorPositions.get(WHITE);
        List<Position> availableBlackPositions = colorPositions.get(BLACK);

        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < newGameConfig.getArcherCount(); i++) {
            units.add(createUnit(ARCHER, WHITE, availableWhitePositions.removeFirst()));
            units.add(createUnit(ARCHER, BLACK, availableBlackPositions.removeFirst()));
        }

        for (int i = 0; i < newGameConfig.getCannonCount(); i++) {
            units.add(createUnit(CANNON, WHITE, availableWhitePositions.removeFirst()));
            units.add(createUnit(CANNON, BLACK, availableBlackPositions.removeFirst()));
        }

        for (int i = 0; i < newGameConfig.getTransportCount(); i++) {
            units.add(createUnit(TRANSPORT, WHITE, availableWhitePositions.removeFirst()));
            units.add(createUnit(TRANSPORT, BLACK, availableBlackPositions.removeFirst()));
        }
        units.forEach(unit -> unit.setGame(game));
        unitService.saveAll(units);
        return units;
    }

    private Unit createUnit(UnitType type, Color color, Position position) {
        Unit unit = new Unit();
        unit.setType(type);
        unit.setColor(color);
        unit.setPosition(position);
        unit.setStatus(ACTIVE);
        return unit;
    }
}
