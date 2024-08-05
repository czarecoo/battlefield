package com.czareg.battlefield.feature.unit;

import com.czareg.battlefield.feature.unit.entity.Position;
import com.czareg.battlefield.feature.unit.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public boolean existsActiveByPosition(Position position) {
        return unitRepository.existsActiveByPosition(position);
    }

    public Optional<Unit> findActiveByPosition(Position position) {
        return unitRepository.findActiveByPosition(position);
    }

    public Optional<Unit> findById(Long unitId) {
        return unitRepository.findById(unitId);
    }

    public void saveAll(List<Unit> entities) {
        unitRepository.saveAll(entities);
    }
}
