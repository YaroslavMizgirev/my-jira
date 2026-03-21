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
import ru.mymsoft.my_jira.dto.CreatePermissionDto;
import ru.mymsoft.my_jira.dto.PermissionDto;
import ru.mymsoft.my_jira.dto.UpdatePermissionDto;
import ru.mymsoft.my_jira.service.PermissionService;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Tag(name = "Permissions", description = "API для управления разрешениями")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового разрешения")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Разрешение создано"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Разрешение с таким именем уже существует")
    })
    public PermissionDto create(@Valid @RequestBody CreatePermissionDto request) {
        return permissionService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список разрешений с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<PermissionDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return permissionService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение разрешения по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Разрешение найдено"),
        @ApiResponse(responseCode = "404", description = "Разрешение не найдено")
    })
    public PermissionDto getById(@PathVariable Long id) {
        return permissionService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление разрешения")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Разрешение обновлено"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Разрешение не найдено"),
        @ApiResponse(responseCode = "409", description = "Разрешение с таким именем уже существует")
    })
    public PermissionDto update(@PathVariable Long id, @Valid @RequestBody UpdatePermissionDto request) {
        return permissionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление разрешения")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Разрешение удалено"),
        @ApiResponse(responseCode = "404", description = "Разрешение не найдено")
    })
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }
}
