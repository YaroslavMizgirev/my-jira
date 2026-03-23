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
import ru.mymsoft.my_jira.dto.CreateIssueWatcherDto;
import ru.mymsoft.my_jira.dto.IssueWatcherDto;
import ru.mymsoft.my_jira.service.IssueWatcherService;

@RestController
@RequestMapping("/api/v1/issue-watchers")
@RequiredArgsConstructor
@Tag(name = "Issue Watchers", description = "API для управления подписчиками задач")
public class IssueWatcherController {

    private final IssueWatcherService issueWatcherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Подписаться на задачу")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Подписка добавлена"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены"),
        @ApiResponse(responseCode = "409", description = "Пользователь уже подписан на задачу")
    })
    public IssueWatcherDto add(@Valid @RequestBody CreateIssueWatcherDto request) {
        return issueWatcherService.add(request);
    }

    @GetMapping("/issues/{issueId}")
    @Operation(summary = "Подписчики задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public List<IssueWatcherDto> listByIssue(@PathVariable Long issueId) {
        return issueWatcherService.listByIssue(issueId);
    }

    @DeleteMapping("/issues/{issueId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Отписаться от задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Подписка удалена"),
        @ApiResponse(responseCode = "404", description = "Подписка не найдена")
    })
    public void remove(@PathVariable Long issueId, @PathVariable Long userId) {
        issueWatcherService.remove(issueId, userId);
    }
}
