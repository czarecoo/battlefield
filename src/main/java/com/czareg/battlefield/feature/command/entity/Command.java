package com.czareg.battlefield.feature.command.entity;

import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.common.enums.CommandType;
import com.czareg.battlefield.feature.unit.entity.Unit;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", nullable = false)
    @JsonIgnore
    private Unit unit;

    private Instant createdAt;
    private Instant cooldownFinishingAt;

    @Enumerated(EnumType.STRING)
    private CommandType type;

    @AttributeOverrides(value = {
            @AttributeOverride(name = "x", column = @Column(name = "before_x")),
            @AttributeOverride(name = "y", column = @Column(name = "before_y"))
    })
    @Embedded
    private Position before;

    @AttributeOverrides(value = {
            @AttributeOverride(name = "x", column = @Column(name = "target_x")),
            @AttributeOverride(name = "y", column = @Column(name = "target_y"))
    })
    @Embedded
    private Position target;

    public static Command of(Position current, Position target, Unit unit, int cooldownInMillis, CommandType commandType) {
        Instant now = Instant.now();
        Command command = new Command();
        command.setUnit(unit);
        command.setCreatedAt(now);
        command.setCooldownFinishingAt(now.plusMillis(cooldownInMillis));
        command.setType(commandType);
        command.setBefore(current);
        command.setTarget(target);
        return command;
    }
}