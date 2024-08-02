package com.czareg.chessboard.feature.unit.types;

import com.czareg.chessboard.feature.command.Command;
import com.czareg.chessboard.feature.unit.Position;
import com.czareg.chessboard.feature.unit.Unit;

public class Archer extends Unit {

    public Archer(Long id, Position position) {
        super(id, position, UnitType.ARCHER);
    }

    @Override
    public void executeCommand(Command command) {
        switch (command.getCommandType()) {
            case MOVE:
                // Logic to move the archer
                this.setPosition(command.getTargetPosition());
                this.incrementMoveCount();
                break;
            case SHOOT:
                // Logic to shoot from the archer
                // Implement shooting logic here
                break;
            default:
                throw new UnsupportedOperationException("Invalid command for Archer");
        }
    }
}
