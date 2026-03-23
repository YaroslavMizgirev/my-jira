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
import ru.mymsoft.my_jira.dto.CreateIssueDto;
import ru.mymsoft.my_jira.dto.IssueDto;
import ru.mymsoft.my_jira.dto.UpdateIssueDto;
import ru.mymsoft.my_jira.service.IssueService;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
@Tag(name = "Issues", description = "API для управления задачами")
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание новой задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Задача создана"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Проект или пользователь не найдены"),
        @ApiResponse(responseCode = "409", description = "Задача с таким ключом уже существует")
    })
    public IssueDto create(@Valid @RequestBody CreateIssueDto request) {
        return issueService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список задач")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен")
    })
    public Page<IssueDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
        return issueService.list(filter, pageable);
    }

    @GetMapping("/projects/{projectId}")
    @Operation(summary = "Задачи проекта")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Проект не найден")
    })
    public Page<IssueDto> listByProject(@PathVariable Long projectId, Pageable pageable) {
        return issueService.listByProject(projectId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение задачи по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Задача найдена"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public IssueDto getById(@PathVariable Long id) {
        return issueService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Задача обновлена"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public IssueDto update(@PathVariable Long id, @Valid @RequestBody UpdateIssueDto request) {
        return issueService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Задача удалена"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public void delete(@PathVariable Long id) {
        issueService.delete(id);
    }
}
