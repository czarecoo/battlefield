package com.czareg.battlefield.feature.unit.types;

import com.czareg.battlefield.feature.command.Command;
import com.czareg.battlefield.feature.unit.Position;
import com.czareg.battlefield.feature.unit.Unit;

public class Cannon extends Unit {

    public Cannon(Long id, Position position) {
        super(id, position, UnitType.CANNON);
    }

    @Override
    public void executeCommand(Command command) {
        switch (command.getCommandType()) {
            case SHOOT:
                // Logic to shoot from the cannon
                // Implement shooting logic here
                break;
            default:
                throw new UnsupportedOperationException("Invalid command for Cannon");
        }
    }
}
