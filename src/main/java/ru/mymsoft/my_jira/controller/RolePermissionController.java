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
import ru.mymsoft.my_jira.dto.CreateRolePermissionDto;
import ru.mymsoft.my_jira.dto.RolePermissionDto;
import ru.mymsoft.my_jira.service.RolePermissionService;

@RestController
@RequestMapping("/api/v1/role-permissions")
@RequiredArgsConstructor
@Tag(name = "Role Permissions", description = "API для управления разрешениями ролей")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Назначить разрешение роли")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Разрешение назначено"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Роль или разрешение не найдены"),
        @ApiResponse(responseCode = "409", description = "Разрешение уже назначено данной роли")
    })
    public RolePermissionDto assign(@Valid @RequestBody CreateRolePermissionDto request) {
        return rolePermissionService.assign(request);
    }

    @GetMapping("/roles/{roleId}")
    @Operation(summary = "Получить список разрешений роли")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Роль не найдена")
    })
    public List<RolePermissionDto> listByRole(@PathVariable Long roleId) {
        return rolePermissionService.listByRole(roleId);
    }

    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Отозвать разрешение у роли")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Разрешение отозвано"),
        @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    public void revoke(@PathVariable Long roleId, @PathVariable Long permissionId) {
        rolePermissionService.revoke(roleId, permissionId);
    }
}
