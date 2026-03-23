package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateWorkflowTransitionDto(
    @NotNull(message = "ID cannot be null")
    Long id,
    @NotBlank(message = "Name cannot be blank")
    String name,
    @NotNull(message = "fromStatusId cannot be null")
    Long fromStatusId,
    @NotNull(message = "toStatusId cannot be null")
    Long toStatusId
) {}
