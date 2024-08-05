package com.czareg.battlefield.feature.unit;

import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public boolean isPositionOccupied(Position position) {
        return unitRepository.existsByPosition(position);
    }

    public Optional<Unit> findByPosition(Position position) {
        return unitRepository.findByPosition(position);
    }

    public Optional<Unit> findById(Long unitId) {
        return unitRepository.findById(unitId);
    }

    public <S extends Unit> S save(S entity) {
        return unitRepository.save(entity);
    }
}
