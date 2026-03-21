package ru.mymsoft.my_jira.dto;

public record WorkflowTransitionDto(
    Long id,
    Long workflowId,
    String name,
    Long fromStatusId,
    Long toStatusId
) {}
