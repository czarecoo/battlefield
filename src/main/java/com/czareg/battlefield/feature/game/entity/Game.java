package com.czareg.battlefield.feature.game.entity;

import com.czareg.battlefield.feature.game.dto.response.GameDTO;
import com.czareg.battlefield.feature.unit.entity.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

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

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Unit> units;

    private Instant started;

    public GameDTO toDTO() {
        return GameDTO.builder()
                .id(id)
                .board(board.toDTO())
                .units(units.stream()
                        .map(Unit::toDTO)
                        .toList())
                .started(started)
                .build();
    }
}
