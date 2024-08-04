package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.feature.command.entity.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<CommandHistory, Long> {
}