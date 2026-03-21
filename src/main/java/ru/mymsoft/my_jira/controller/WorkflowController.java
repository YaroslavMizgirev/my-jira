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
import ru.mymsoft.my_jira.dto.CreateWorkflowDto;
import ru.mymsoft.my_jira.dto.UpdateWorkflowDto;
import ru.mymsoft.my_jira.dto.WorkflowDto;
import ru.mymsoft.my_jira.service.WorkflowService;

@RestController
@RequestMapping("/api/v1/workflows")
@RequiredArgsConstructor
@Tag(name = "Workflows", description = "API для управления workflow")
public class WorkflowController {

    private final WorkflowService workflowService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Workflow создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Workflow с таким именем уже существует")
    })
    public WorkflowDto create(@Valid @RequestBody CreateWorkflowDto request) {
        return workflowService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список workflow с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<WorkflowDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return workflowService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение workflow по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Workflow найден"),
        @ApiResponse(responseCode = "404", description = "Workflow не найден")
    })
    public WorkflowDto getById(@PathVariable Long id) {
        return workflowService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Workflow обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Workflow не найден"),
        @ApiResponse(responseCode = "409", description = "Workflow с таким именем уже существует")
    })
    public WorkflowDto update(@PathVariable Long id, @Valid @RequestBody UpdateWorkflowDto request) {
        return workflowService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Workflow удалён"),
        @ApiResponse(responseCode = "404", description = "Workflow не найден")
    })
    public void delete(@PathVariable Long id) {
        workflowService.delete(id);
    }
}
