package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record CreateProjectWorkflowDefaultDto(
    @NotNull Long projectId,
    @NotNull Long issueTypeId,
    @NotNull Long workflowId
) {}
