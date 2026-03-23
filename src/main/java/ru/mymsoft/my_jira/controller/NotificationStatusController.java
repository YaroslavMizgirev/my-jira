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
import ru.mymsoft.my_jira.dto.CreateNotificationStatusDto;
import ru.mymsoft.my_jira.dto.NotificationStatusDto;
import ru.mymsoft.my_jira.dto.UpdateNotificationStatusDto;
import ru.mymsoft.my_jira.service.NotificationStatusService;

@RestController
@RequestMapping("/api/v1/notification-statuses")
@RequiredArgsConstructor
@Tag(name = "Notification Statuses", description = "API для управления статусами уведомлений")
public class NotificationStatusController {

    private final NotificationStatusService notificationStatusService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового статуса уведомления")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Статус уведомления создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Статус с таким именем уже существует")
    })
    public NotificationStatusDto create(@Valid @RequestBody CreateNotificationStatusDto request) {
        return notificationStatusService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список статусов уведомлений с фильтрацией")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<NotificationStatusDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return notificationStatusService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение статуса уведомления по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Статус найден"),
        @ApiResponse(responseCode = "404", description = "Статус не найден")
    })
    public NotificationStatusDto getById(@PathVariable Long id) {
        return notificationStatusService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление статуса уведомления")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Статус обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Статус не найден"),
        @ApiResponse(responseCode = "409", description = "Статус с таким именем уже существует")
    })
    public NotificationStatusDto update(@PathVariable Long id, @Valid @RequestBody UpdateNotificationStatusDto request) {
        return notificationStatusService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление статуса уведомления")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Статус удалён"),
        @ApiResponse(responseCode = "404", description = "Статус не найден")
    })
    public void delete(@PathVariable Long id) {
        notificationStatusService.delete(id);
    }
}
