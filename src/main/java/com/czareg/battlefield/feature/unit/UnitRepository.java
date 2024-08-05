package com.czareg.battlefield.feature.unit;

import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("SELECT COUNT(u) > 0 FROM Unit u WHERE u.position = :position AND u.status = 'ACTIVE'")
    boolean existsActiveByPosition(@Param("position") Position position);

    @Query("SELECT u FROM Unit u WHERE u.position = :position AND u.status = 'ACTIVE'")
    Optional<Unit> findActiveByPosition(@Param("position") Position position);
}