package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.game.entity.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game getFirstByOrderByIdDesc();

    @Query(value = "SELECT g.id FROM Game g ORDER BY g.id DESC")
    List<Long> findTopId(Pageable pageable);
}