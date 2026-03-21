package ru.mymsoft.my_jira.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.ActivityLogDto;
import ru.mymsoft.my_jira.service.ActivityLogService;

@RestController
@RequestMapping("/api/v1/activity-log")
@RequiredArgsConstructor
@Tag(name = "Activity Log", description = "API для просмотра журнала активности задач")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @GetMapping("/issues/{issueId}")
    @Operation(summary = "История активности по задаче")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public List<ActivityLogDto> listByIssue(@PathVariable Long issueId) {
        return activityLogService.listByIssue(issueId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение записи журнала по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Запись найдена"),
        @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    public ActivityLogDto getById(@PathVariable Long id) {
        return activityLogService.getById(id);
    }
}
