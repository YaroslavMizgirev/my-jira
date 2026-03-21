package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record CreateProjectMemberDto(
    @NotNull Long projectId,
    @NotNull Long groupId,
    @NotNull Long roleId
) {}
