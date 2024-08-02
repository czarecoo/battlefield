package com.czareg.chessboard.feature.unit.types;

import com.czareg.chessboard.feature.command.Command;
import com.czareg.chessboard.feature.unit.Position;
import com.czareg.chessboard.feature.unit.Unit;

public class Transport extends Unit {

    public Transport(Long id, Position position) {
        super(id, position, UnitType.TRANSPORT);
    }

    @Override
    public void executeCommand(Command command) {
        switch (command.getCommandType()) {
            case MOVE:
                // Logic to move the transport
                this.setPosition(command.getTargetPosition());
                this.incrementMoveCount();
                break;
            default:
                throw new UnsupportedOperationException("Invalid command for Transport");
        }
    }
}
