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
import ru.mymsoft.my_jira.dto.AttachmentDto;
import ru.mymsoft.my_jira.dto.CreateAttachmentDto;
import ru.mymsoft.my_jira.service.AttachmentService;

@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
@Tag(name = "Attachments", description = "API для управления вложениями задач")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить вложение к задаче")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Вложение добавлено"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Задача, пользователь или тип файла не найдены")
    })
    public AttachmentDto create(@Valid @RequestBody CreateAttachmentDto request) {
        return attachmentService.create(request);
    }

    @GetMapping("/issues/{issueId}")
    @Operation(summary = "Вложения задачи")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public List<AttachmentDto> listByIssue(@PathVariable Long issueId) {
        return attachmentService.listByIssue(issueId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение вложения по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Вложение найдено"),
        @ApiResponse(responseCode = "404", description = "Вложение не найдено")
    })
    public AttachmentDto getById(@PathVariable Long id) {
        return attachmentService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление вложения")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Вложение удалено"),
        @ApiResponse(responseCode = "404", description = "Вложение не найдено")
    })
    public void delete(@PathVariable Long id) {
        attachmentService.delete(id);
    }
}
