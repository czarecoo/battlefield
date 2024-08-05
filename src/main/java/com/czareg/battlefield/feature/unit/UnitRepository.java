package com.czareg.battlefield.feature.unit;

import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    boolean existsByPosition(Position position);

    Optional<Unit> findByPosition(Position position);
}