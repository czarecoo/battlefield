package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.feature.command.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    Optional<Command> findFirstByUnitIdOrderByIdDesc(Long unitId);
}