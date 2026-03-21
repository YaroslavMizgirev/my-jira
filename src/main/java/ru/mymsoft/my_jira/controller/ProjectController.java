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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateProjectDto;
import ru.mymsoft.my_jira.dto.ProjectDto;
import ru.mymsoft.my_jira.dto.UpdateProjectDto;
import ru.mymsoft.my_jira.service.ProjectService;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Projects", description = "API для управления проектами")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового проекта")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Проект создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Пользователь или workflow не найдены"),
        @ApiResponse(responseCode = "409", description = "Проект с таким именем или ключом уже существует")
    })
    public ProjectDto create(@Valid @RequestBody CreateProjectDto request) {
        return projectService.create(request);
    }

    @GetMapping
    @Operation(summary = "Получение списка проектов")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен")
    })
    public Page<ProjectDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
        return projectService.list(filter, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение проекта по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Проект найден"),
        @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    public ProjectDto getById(@PathVariable Long id) {
        return projectService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление проекта")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Проект обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Проект не найден"),
        @ApiResponse(responseCode = "409", description = "Проект с таким именем или ключом уже существует")
    })
    public ProjectDto update(@PathVariable Long id, @Valid @RequestBody UpdateProjectDto request) {
        return projectService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление проекта")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Проект удалён"),
        @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    public void delete(@PathVariable Long id) {
        projectService.delete(id);
    }
}
