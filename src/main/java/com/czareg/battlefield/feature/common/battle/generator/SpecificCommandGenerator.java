package com.czareg.battlefield.feature.common.battle.generator;

import com.czareg.battlefield.feature.common.battle.pojo.SpecificCommand;
import com.czareg.battlefield.feature.unit.entity.Unit;

import java.util.List;

public interface SpecificCommandGenerator {

    List<SpecificCommand> generate(Unit unit);
}
