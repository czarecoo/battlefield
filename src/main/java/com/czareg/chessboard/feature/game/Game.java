package com.czareg.chessboard.feature.game;

import com.czareg.chessboard.feature.unit.Position;
import com.czareg.chessboard.feature.unit.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Board board;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "position_id")
    private Map<Position, Unit> grid;
}
