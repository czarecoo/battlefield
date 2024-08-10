package com.czareg.battlefield.feature.game.entity;

import com.czareg.battlefield.feature.common.entity.Board;
import com.czareg.battlefield.feature.game.dto.response.GameDTO;
import com.czareg.battlefield.feature.unit.entity.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Board board;

    @OneToMany(mappedBy = "game", fetch = EAGER)
    private List<Unit> units;

    private Instant createdAt;

    public GameDTO toDTO() {
        return GameDTO.builder()
                .id(id)
                .board(board.toDTO())
                .units(units.stream()
                        .map(Unit::toDTO)
                        .toList())
                .createdAt(createdAt)
                .build();
    }
}
