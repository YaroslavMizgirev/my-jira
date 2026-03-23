package ru.mymsoft.my_jira.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import ru.mymsoft.my_jira.dto.CreateWorkflowStatusDto;
import ru.mymsoft.my_jira.dto.WorkflowStatusDto;
import ru.mymsoft.my_jira.service.WorkflowStatusService;

@RestController
@RequestMapping("/api/v1/workflow-statuses")
@RequiredArgsConstructor
@Tag(name = "Workflow Statuses", description = "API для управления статусами workflow")
public class WorkflowStatusController {

    private final WorkflowStatusService workflowStatusService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить статус в workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Статус добавлен"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Workflow или статус не найдены"),
        @ApiResponse(responseCode = "409", description = "Статус уже добавлен в этот workflow")
    })
    public WorkflowStatusDto add(@Valid @RequestBody CreateWorkflowStatusDto request) {
        return workflowStatusService.add(request);
    }

    @GetMapping("/workflows/{workflowId}")
    @Operation(summary = "Получить статусы workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Workflow не найден")
    })
    public List<WorkflowStatusDto> listByWorkflow(@PathVariable Long workflowId) {
        return workflowStatusService.listByWorkflow(workflowId);
    }

    @DeleteMapping("/workflows/{workflowId}/statuses/{statusId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить статус из workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Статус удалён из workflow"),
        @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    public void remove(@PathVariable Long workflowId, @PathVariable Long statusId) {
        workflowStatusService.remove(workflowId, statusId);
    }
}
