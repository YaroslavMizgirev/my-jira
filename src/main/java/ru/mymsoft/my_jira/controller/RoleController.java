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
import ru.mymsoft.my_jira.dto.CreateRoleDto;
import ru.mymsoft.my_jira.dto.RoleDto;
import ru.mymsoft.my_jira.dto.UpdateRoleDto;
import ru.mymsoft.my_jira.service.RoleService;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "API для управления ролями")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание новой роли")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Роль создана"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Роль с таким именем уже существует")
    })
    public RoleDto create(@Valid @RequestBody CreateRoleDto request) {
        return roleService.create(request);
    }

    @GetMapping
    @Operation(summary = "Список ролей с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<RoleDto> list(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String namePart,
        Pageable pageable
    ) {
        return roleService.list(namePart, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение роли по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Роль найдена"),
        @ApiResponse(responseCode = "404", description = "Роль не найдена")
    })
    public RoleDto getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление роли")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Роль обновлена"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Роль не найдена"),
        @ApiResponse(responseCode = "409", description = "Роль с таким именем уже существует")
    })
    public RoleDto update(@PathVariable Long id, @Valid @RequestBody UpdateRoleDto request) {
        return roleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление роли")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Роль удалена"),
        @ApiResponse(responseCode = "404", description = "Роль не найдена")
    })
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}
