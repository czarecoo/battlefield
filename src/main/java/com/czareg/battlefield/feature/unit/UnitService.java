package com.czareg.battlefield.feature.unit;

import com.czareg.battlefield.feature.common.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public boolean existsActiveByPositionAndGameId(Position position, Long gameId) {
        return unitRepository.existsActiveByPositionAndGameId(position, gameId);
    }

    public Optional<Unit> findActiveByPositionAndGameId(Position position, Long gameId) {
        return unitRepository.findActiveByPositionAndGameId(position, gameId);
    }

    public Optional<Unit> findById(Long unitId) {
        return unitRepository.findById(unitId);
    }

    public void saveAll(List<Unit> entities) {
        unitRepository.saveAll(entities);
    }
}
