package ru.mymsoft.my_jira.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreatePriorityDto;
import ru.mymsoft.my_jira.dto.PriorityDto;
import ru.mymsoft.my_jira.dto.UpdatePriorityDto;
import ru.mymsoft.my_jira.service.PriorityService;

@RestController
@RequestMapping("/api/v1/priorities")
@RequiredArgsConstructor
@Tag(name = "Priorities", description = "API для управления приоритетами задач")
public class PriorityController {

    private final PriorityService priorityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового приоритета")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Приоритет создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Приоритет с таким именем или уровнем уже существует")
    })
    public PriorityDto create(@Valid @RequestBody CreatePriorityDto request) {
        return priorityService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список приоритетов с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<PriorityDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return priorityService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение приоритета по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Приоритет найден"),
        @ApiResponse(responseCode = "404", description = "Приоритет не найден")
    })
    public PriorityDto getById(@PathVariable Long id) {
        return priorityService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление приоритета")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Приоритет обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Приоритет не найден"),
        @ApiResponse(responseCode = "409", description = "Приоритет с таким именем или уровнем уже существует")
    })
    public PriorityDto update(@PathVariable Long id, @Valid @RequestBody UpdatePriorityDto request) {
        return priorityService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление приоритета")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Приоритет удалён"),
        @ApiResponse(responseCode = "404", description = "Приоритет не найден")
    })
    public void delete(@PathVariable Long id) {
        priorityService.delete(id);
    }
}
