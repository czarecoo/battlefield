package com.czareg.battlefield.feature.command;

import com.czareg.battlefield.feature.command.dto.request.RandomCommandRequestDTO;
import com.czareg.battlefield.feature.command.dto.request.SpecificCommandRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.ACCEPTED;

@Validated
@RestController
@RequestMapping("/commands")
@RequiredArgsConstructor
public class CommandController {

    private final CommandService commandService;

    @PostMapping("/specific")
    @ResponseStatus(ACCEPTED)
    public void executeSpecificCommand(@Valid @RequestBody @NotNull SpecificCommandRequestDTO specificCommandDTO) {
        commandService.executeSpecificCommand(specificCommandDTO);
    }

    @PostMapping("/random")
    @ResponseStatus(ACCEPTED)
    public void executeRandomCommand(@Valid @RequestBody @NotNull RandomCommandRequestDTO randomCommandDTO) {
        commandService.executeRandomCommand(randomCommandDTO);
    }
}
