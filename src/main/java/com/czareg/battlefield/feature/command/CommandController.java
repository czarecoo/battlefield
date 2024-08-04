package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.feature.game.dto.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.game.dto.SpecificCommandRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/commands")
@RequiredArgsConstructor
public class CommandController {

    private final CommandService commandService;

    @PostMapping("/specific")
    public void executeSpecificCommand(@Valid @RequestBody @NotNull SpecificCommandRequestDTO specificCommandDTO) {
        commandService.executeSpecificCommand(specificCommandDTO);
    }

    @PostMapping("/random")
    public void executeRandomCommand(@Valid @RequestBody @NotNull RandomCommandRequestDTO randomCommandDTO) {
        commandService.executeRandomCommand(randomCommandDTO);
    }
}
