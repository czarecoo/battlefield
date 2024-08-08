package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.feature.command.entity.Command;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    @Query("SELECT c.cooldownFinishingAt FROM Command c WHERE c.id IN (" +
           "SELECT c2.id FROM Command c2 WHERE c2.unit.id = :unitId ORDER BY c2.id DESC)")
    Optional<Instant> getLastCooldownFinishingTimeForUnitId(Long unitId, Limit limit);
}