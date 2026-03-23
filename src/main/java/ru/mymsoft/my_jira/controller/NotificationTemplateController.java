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
import ru.mymsoft.my_jira.dto.CreateNotificationTemplateDto;
import ru.mymsoft.my_jira.dto.NotificationTemplateDto;
import ru.mymsoft.my_jira.dto.UpdateNotificationTemplateDto;
import ru.mymsoft.my_jira.service.NotificationTemplateService;

@RestController
@RequestMapping("/api/v1/notification-templates")
@RequiredArgsConstructor
@Tag(name = "Notification Templates", description = "API для управления шаблонами уведомлений")
public class NotificationTemplateController {

    private final NotificationTemplateService notificationTemplateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового шаблона уведомления")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Шаблон создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Шаблон с таким именем уже существует")
    })
    public NotificationTemplateDto create(@Valid @RequestBody CreateNotificationTemplateDto request) {
        return notificationTemplateService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список шаблонов уведомлений с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<NotificationTemplateDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return notificationTemplateService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение шаблона уведомления по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Шаблон найден"),
        @ApiResponse(responseCode = "404", description = "Шаблон не найден")
    })
    public NotificationTemplateDto getById(@PathVariable Long id) {
        return notificationTemplateService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление шаблона уведомления")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Шаблон обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Шаблон не найден"),
        @ApiResponse(responseCode = "409", description = "Шаблон с таким именем уже существует")
    })
    public NotificationTemplateDto update(@PathVariable Long id, @Valid @RequestBody UpdateNotificationTemplateDto request) {
        return notificationTemplateService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление шаблона уведомления")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Шаблон удалён"),
        @ApiResponse(responseCode = "404", description = "Шаблон не найден")
    })
    public void delete(@PathVariable Long id) {
        notificationTemplateService.delete(id);
    }
}
