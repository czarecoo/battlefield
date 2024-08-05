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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Position position;

    @Enumerated(EnumType.STRING)
    private UnitType type;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
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
