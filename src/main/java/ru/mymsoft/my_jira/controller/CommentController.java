package ru.mymsoft.my_jira.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import ru.mymsoft.my_jira.dto.CommentDto;
import ru.mymsoft.my_jira.dto.CreateCommentDto;
import ru.mymsoft.my_jira.dto.UpdateCommentDto;
import ru.mymsoft.my_jira.service.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "API для управления комментариями к задачам")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить комментарий")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Комментарий добавлен"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Задача или пользователь не найдены")
    })
    public CommentDto create(@Valid @RequestBody CreateCommentDto request) {
        return commentService.create(request);
    }

    @GetMapping("/issues/{issueId}")
    @Operation(summary = "Комментарии к задаче")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    public List<CommentDto> listByIssue(@PathVariable Long issueId) {
        return commentService.listByIssue(issueId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение комментария по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Комментарий найден"),
        @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    public CommentDto getById(@PathVariable Long id) {
        return commentService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление комментария")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Комментарий обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    public CommentDto update(@PathVariable Long id, @Valid @RequestBody UpdateCommentDto request) {
        return commentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление комментария")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Комментарий удалён"),
        @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }
}
