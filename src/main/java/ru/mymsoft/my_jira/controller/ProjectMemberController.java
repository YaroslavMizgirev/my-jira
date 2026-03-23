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
import ru.mymsoft.my_jira.dto.CreateProjectMemberDto;
import ru.mymsoft.my_jira.dto.ProjectMemberDto;
import ru.mymsoft.my_jira.service.ProjectMemberService;

@RestController
@RequestMapping("/api/v1/project-members")
@RequiredArgsConstructor
@Tag(name = "Project Members", description = "API для управления участниками проекта")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить группу с ролью в проект")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Участник добавлен"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Проект, группа или роль не найдены"),
        @ApiResponse(responseCode = "409", description = "Такая комбинация уже существует")
    })
    public ProjectMemberDto add(@Valid @RequestBody CreateProjectMemberDto request) {
        return projectMemberService.add(request);
    }

    @GetMapping("/projects/{projectId}")
    @Operation(summary = "Получить участников проекта")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    public List<ProjectMemberDto> listByProject(@PathVariable Long projectId) {
        return projectMemberService.listByProject(projectId);
    }

    @DeleteMapping("/projects/{projectId}/groups/{groupId}/roles/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить группу с ролью из проекта")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Участник удалён"),
        @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    public void remove(@PathVariable Long projectId, @PathVariable Long groupId, @PathVariable Long roleId) {
        projectMemberService.remove(projectId, groupId, roleId);
    }
}
