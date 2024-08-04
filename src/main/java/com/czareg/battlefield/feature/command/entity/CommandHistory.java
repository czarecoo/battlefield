package com.czareg.battlefield.feature.command.entity;

import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    private Instant commandTime;

    @Enumerated(EnumType.STRING)
    private CommandType commandType;

    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "currentX")),
            @AttributeOverride(name = "y", column = @Column(name = "currentY"))
    })
    @Embedded
    private Position current;

    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "targetX")),
            @AttributeOverride(name = "y", column = @Column(name = "targetY"))
    })
    @Embedded
    private Position target;
}