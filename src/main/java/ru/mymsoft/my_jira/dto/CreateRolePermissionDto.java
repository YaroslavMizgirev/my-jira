package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record CreateRolePermissionDto(
    @NotNull(message = "roleId cannot be null")
    Long roleId,
    @NotNull(message = "permissionId cannot be null")
    Long permissionId
) {}
