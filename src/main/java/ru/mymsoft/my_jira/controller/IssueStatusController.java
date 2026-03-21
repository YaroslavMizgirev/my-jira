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
import ru.mymsoft.my_jira.dto.CreateIssueStatusDto;
import ru.mymsoft.my_jira.dto.IssueStatusDto;
import ru.mymsoft.my_jira.dto.UpdateIssueStatusDto;
import ru.mymsoft.my_jira.service.IssueStatusService;

@RestController
@RequestMapping("/api/v1/issue-statuses")
@RequiredArgsConstructor
@Tag(name = "Issue Statuses", description = "API для управления статусами задач")
public class IssueStatusController {

    private final IssueStatusService issueStatusService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового статуса задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Статус успешно создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Статус с таким именем уже существует")
    })
    public IssueStatusDto create(@Valid @RequestBody CreateIssueStatusDto request) {
        return issueStatusService.createIssueStatus(request);
    }

    @GetMapping
    @Operation(summary = "Список статусов задач с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<IssueStatusDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return issueStatusService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение статуса задачи по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Статус найден"),
        @ApiResponse(responseCode = "404", description = "Статус не найден")
    })
    public IssueStatusDto getById(@PathVariable Long id) {
        return issueStatusService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление статуса задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Статус обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Статус не найден"),
        @ApiResponse(responseCode = "409", description = "Статус с таким именем уже существует")
    })
    public IssueStatusDto update(@PathVariable Long id, @Valid @RequestBody UpdateIssueStatusDto request) {
        return issueStatusService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление статуса задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Статус удалён"),
        @ApiResponse(responseCode = "404", description = "Статус не найден")
    })
    public void delete(@PathVariable Long id) {
        issueStatusService.delete(id);
    }
}
