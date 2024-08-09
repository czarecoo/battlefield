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

    @Query("SELECT COUNT(u) > 0 FROM Unit u WHERE u.position = :position AND u.status = 'ACTIVE' AND u.game.id = :gameId")
    boolean existsActiveByPositionAndGameId(@Param("position") Position position, @Param("gameId") Long gameId);

    @Query("SELECT u FROM Unit u JOIN FETCH u.game WHERE u.position = :position AND u.status = 'ACTIVE' AND u.game.id = :gameId")
    Optional<Unit> findActiveByPositionAndGameId(@Param("position") Position position, @Param("gameId") Long gameId);

    @Query("SELECT COUNT(u) > 0 FROM Unit u WHERE u.id = :unitId AND u.status = 'ACTIVE'")
    boolean isActiveById(@Param("unitId") Long unitId);

    @Query("SELECT CASE WHEN g.id = (SELECT MAX(g2.id) FROM Game g2) THEN TRUE ELSE FALSE END " +
           "FROM Unit u JOIN u.game g WHERE u.id = :unitId")
    boolean isUnitInCurrentGame(@Param("unitId") Long unitId);
}