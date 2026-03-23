package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserGroupDto(
    @NotNull Long userId,
    @NotNull Long groupId
) {}
