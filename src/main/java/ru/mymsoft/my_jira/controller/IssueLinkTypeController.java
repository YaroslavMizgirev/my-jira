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
import ru.mymsoft.my_jira.dto.CreateIssueLinkTypeDto;
import ru.mymsoft.my_jira.dto.IssueLinkTypeDto;
import ru.mymsoft.my_jira.dto.UpdateIssueLinkTypeDto;
import ru.mymsoft.my_jira.service.IssueLinkTypeService;

@RestController
@RequestMapping("/api/v1/issue-link-types")
@RequiredArgsConstructor
@Tag(name = "Issue Link Types", description = "API для управления типами связей между задачами")
public class IssueLinkTypeController {

    private final IssueLinkTypeService issueLinkTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового типа связи")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Тип связи создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Тип связи с таким именем уже существует")
    })
    public IssueLinkTypeDto create(@Valid @RequestBody CreateIssueLinkTypeDto request) {
        return issueLinkTypeService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список типов связей с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<IssueLinkTypeDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return issueLinkTypeService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение типа связи по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Тип связи найден"),
        @ApiResponse(responseCode = "404", description = "Тип связи не найден")
    })
    public IssueLinkTypeDto getById(@PathVariable Long id) {
        return issueLinkTypeService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление типа связи")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Тип связи обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Тип связи не найден"),
        @ApiResponse(responseCode = "409", description = "Тип связи с таким именем уже существует")
    })
    public IssueLinkTypeDto update(@PathVariable Long id, @Valid @RequestBody UpdateIssueLinkTypeDto request) {
        return issueLinkTypeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление типа связи")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Тип связи удалён"),
        @ApiResponse(responseCode = "404", description = "Тип связи не найден")
    })
    public void delete(@PathVariable Long id) {
        issueLinkTypeService.delete(id);
    }
}
