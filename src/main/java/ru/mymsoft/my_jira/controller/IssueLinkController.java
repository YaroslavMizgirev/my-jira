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
import ru.mymsoft.my_jira.dto.CreateIssueLinkDto;
import ru.mymsoft.my_jira.dto.IssueLinkDto;
import ru.mymsoft.my_jira.service.IssueLinkService;

@RestController
@RequestMapping("/api/v1/issue-links")
@RequiredArgsConstructor
@Tag(name = "Issue Links", description = "API для управления связями между задачами")
public class IssueLinkController {

    private final IssueLinkService issueLinkService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать связь между задачами")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Связь создана"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Задача или тип связи не найдены"),
        @ApiResponse(responseCode = "409", description = "Такая связь уже существует")
    })
    public IssueLinkDto create(@Valid @RequestBody CreateIssueLinkDto request) {
        return issueLinkService.create(request);
    }

    @GetMapping("/issues/{issueId}")
    @Operation(summary = "Связи задачи (входящие и исходящие)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public List<IssueLinkDto> listByIssue(@PathVariable Long issueId) {
        return issueLinkService.listByIssue(issueId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение связи по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Связь найдена"),
        @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    public IssueLinkDto getById(@PathVariable Long id) {
        return issueLinkService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление связи")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Связь удалена"),
        @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    public void delete(@PathVariable Long id) {
        issueLinkService.delete(id);
    }
}
