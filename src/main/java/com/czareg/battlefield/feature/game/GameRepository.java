package com.czareg.battlefield.feature.game;

import com.czareg.battlefield.feature.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game getFirstByOrderByIdDesc();
}