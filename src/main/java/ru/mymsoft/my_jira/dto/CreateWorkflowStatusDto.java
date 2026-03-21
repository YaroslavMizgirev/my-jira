package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record CreateWorkflowStatusDto(
    @NotNull(message = "workflowId cannot be null")
    Long workflowId,
    @NotNull(message = "statusId cannot be null")
    Long statusId
) {}
