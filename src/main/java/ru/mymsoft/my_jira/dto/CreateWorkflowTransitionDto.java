package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWorkflowTransitionDto(
    @NotNull(message = "workflowId cannot be null")
    Long workflowId,
    @NotBlank(message = "Name cannot be blank")
    String name,
    @NotNull(message = "fromStatusId cannot be null")
    Long fromStatusId,
    @NotNull(message = "toStatusId cannot be null")
    Long toStatusId
) {}
