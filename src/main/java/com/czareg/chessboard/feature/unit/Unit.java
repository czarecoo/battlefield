package com.czareg.chessboard.feature.unit;

import com.czareg.chessboard.feature.command.Command;
import com.czareg.chessboard.feature.game.Game;
import com.czareg.chessboard.feature.unit.types.UnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Position position;
    @Enumerated(EnumType.STRING)
    private UnitType type;
    private int moveCount;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    public Unit(Long id, Position position, UnitType type) {
        this.id = id;
        this.position = position;
        this.type = type;
    }

    public abstract void executeCommand(Command command);

    public void incrementMoveCount(){
        moveCount++;
    }
}
