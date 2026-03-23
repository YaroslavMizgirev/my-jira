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
import ru.mymsoft.my_jira.dto.CreateFileTypeDto;
import ru.mymsoft.my_jira.dto.FileTypeDto;
import ru.mymsoft.my_jira.dto.UpdateFileTypeDto;
import ru.mymsoft.my_jira.service.FileTypeService;

@RestController
@RequestMapping("/api/v1/file-types")
@RequiredArgsConstructor
@Tag(name = "File Types", description = "API для управления типами файлов")
public class FileTypeController {

    private final FileTypeService fileTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового типа файла")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Тип файла создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Тип файла с таким расширением или MIME-типом уже существует")
    })
    public FileTypeDto create(@Valid @RequestBody CreateFileTypeDto request) {
        return fileTypeService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список типов файлов с фильтрацией по расширению")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<FileTypeDto> list(
        @Parameter(description = "Фильтр по части расширения") @RequestParam(required = false) String extensionPart,
        Pageable pageable
    ) {
        return fileTypeService.list(extensionPart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение типа файла по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Тип файла найден"),
        @ApiResponse(responseCode = "404", description = "Тип файла не найден")
    })
    public FileTypeDto getById(@PathVariable Long id) {
        return fileTypeService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление типа файла")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Тип файла обновлён"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Тип файла не найден"),
        @ApiResponse(responseCode = "409", description = "Тип файла с таким расширением или MIME-типом уже существует")
    })
    public FileTypeDto update(@PathVariable Long id, @Valid @RequestBody UpdateFileTypeDto request) {
        return fileTypeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление типа файла")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Тип файла удалён"),
        @ApiResponse(responseCode = "404", description = "Тип файла не найден")
    })
    public void delete(@PathVariable Long id) {
        fileTypeService.delete(id);
    }
}
