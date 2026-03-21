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
import ru.mymsoft.my_jira.dto.CreateProjectWorkflowDefaultDto;
import ru.mymsoft.my_jira.dto.ProjectWorkflowDefaultDto;
import ru.mymsoft.my_jira.service.ProjectIssueTypeWorkflowDefaultService;

@RestController
@RequestMapping("/api/v1/project-workflow-defaults")
@RequiredArgsConstructor
@Tag(name = "Project Workflow Defaults", description = "API для управления дефолтными workflow в проекте")
public class ProjectWorkflowDefaultController {

    private final ProjectIssueTypeWorkflowDefaultService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Назначить дефолтный workflow для типа задачи в проекте")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Дефолт назначен"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Проект, тип задачи или workflow не найдены"),
        @ApiResponse(responseCode = "409", description = "Такая комбинация уже существует")
    })
    public ProjectWorkflowDefaultDto assign(@Valid @RequestBody CreateProjectWorkflowDefaultDto request) {
        return service.assign(request);
    }

    @GetMapping("/projects/{projectId}")
    @Operation(summary = "Получить дефолты workflow для проекта")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    public List<ProjectWorkflowDefaultDto> listByProject(@PathVariable Long projectId) {
        return service.listByProject(projectId);
    }

    @DeleteMapping("/projects/{projectId}/issue-types/{issueTypeId}/workflows/{workflowId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить дефолтный workflow")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Дефолт удалён"),
        @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    public void remove(@PathVariable Long projectId,
                       @PathVariable Long issueTypeId,
                       @PathVariable Long workflowId) {
        service.remove(projectId, issueTypeId, workflowId);
    }
}
