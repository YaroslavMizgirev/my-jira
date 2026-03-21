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
import ru.mymsoft.my_jira.dto.CreateIssueTypeDto;
import ru.mymsoft.my_jira.dto.IssueTypeDto;
import ru.mymsoft.my_jira.dto.UpdateIssueTypeDto;
import ru.mymsoft.my_jira.service.IssueTypeService;

@RestController
@RequestMapping("/api/v1/issue-types")
@RequiredArgsConstructor
@Tag(name = "Issue Types", description = "API для управления типами задач")
public class IssueTypeController {

    private final IssueTypeService issueTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового типа задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Тип задачи создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Тип задачи с таким именем уже существует")
    })
    public IssueTypeDto create(@Valid @RequestBody CreateIssueTypeDto request) {
        return issueTypeService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список типов задач с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<IssueTypeDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return issueTypeService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение типа задачи по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Тип задачи найден"),
        @ApiResponse(responseCode = "404", description = "Тип задачи не найден")
    })
    public IssueTypeDto getById(@PathVariable Long id) {
        return issueTypeService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление типа задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Тип задачи обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Тип задачи не найден"),
        @ApiResponse(responseCode = "409", description = "Тип задачи с таким именем уже существует")
    })
    public IssueTypeDto update(@PathVariable Long id, @Valid @RequestBody UpdateIssueTypeDto request) {
        return issueTypeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление типа задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Тип задачи удалён"),
        @ApiResponse(responseCode = "404", description = "Тип задачи не найден")
    })
    public void delete(@PathVariable Long id) {
        issueTypeService.delete(id);
    }
}
