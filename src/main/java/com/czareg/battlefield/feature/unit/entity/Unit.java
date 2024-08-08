package com.czareg.battlefield.feature.unit.entity;

import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.Color;
import com.czareg.battlefield.feature.common.enums.Status;
import com.czareg.battlefield.feature.common.enums.UnitType;
import com.czareg.battlefield.feature.game.dto.response.UnitDTO;
import com.czareg.battlefield.feature.game.entity.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_game", columnList = "game_id"),
                @Index(name = "idx_id_status", columnList = "id, status"),
                @Index(name = "idx_position_status_game", columnList = "x, y, status, game_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Position position;

    @Enumerated(STRING)
    private UnitType type;

    @Enumerated(STRING)
    private Color color;

    @Enumerated(STRING)
    private Status status;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

    @Version
    private Integer version;

    public UnitDTO toDTO() {
        return UnitDTO.builder()
                .id(id)
                .position(position.toDTO())
                .type(type)
                .color(color)
                .status(status)
                .build();
    }
}
