package ru.mymsoft.my_jira.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateWorkflowTransitionDto;
import ru.mymsoft.my_jira.dto.UpdateWorkflowTransitionDto;
import ru.mymsoft.my_jira.dto.WorkflowTransitionDto;
import ru.mymsoft.my_jira.service.WorkflowTransitionService;

@RestController
@RequestMapping("/api/v1/workflow-transitions")
@RequiredArgsConstructor
@Tag(name = "Workflow Transitions", description = "API для управления переходами workflow")
public class WorkflowTransitionController {

    private final WorkflowTransitionService workflowTransitionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового перехода workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Переход создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Workflow или статус не найдены"),
        @ApiResponse(responseCode = "409", description = "Переход с таким именем или комбинацией уже существует")
    })
    public WorkflowTransitionDto create(@Valid @RequestBody CreateWorkflowTransitionDto request) {
        return workflowTransitionService.create(request);
    }

    @GetMapping("/workflows/{workflowId}")
    @Operation(summary = "Получить переходы workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Workflow не найден")
    })
    public List<WorkflowTransitionDto> listByWorkflow(@PathVariable Long workflowId) {
        return workflowTransitionService.listByWorkflow(workflowId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение перехода по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Переход найден"),
        @ApiResponse(responseCode = "404", description = "Переход не найден")
    })
    public WorkflowTransitionDto getById(@PathVariable Long id) {
        return workflowTransitionService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление перехода")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Переход обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Переход не найден"),
        @ApiResponse(responseCode = "409", description = "Переход с таким именем уже существует")
    })
    public WorkflowTransitionDto update(@PathVariable Long id, @Valid @RequestBody UpdateWorkflowTransitionDto request) {
        return workflowTransitionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление перехода")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Переход удалён"),
        @ApiResponse(responseCode = "404", description = "Переход не найден")
    })
    public void delete(@PathVariable Long id) {
        workflowTransitionService.delete(id);
    }
}
