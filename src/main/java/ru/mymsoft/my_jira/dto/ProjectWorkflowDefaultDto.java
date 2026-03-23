package ru.mymsoft.my_jira.dto;

public record ProjectWorkflowDefaultDto(
    Long projectId,
    Long issueTypeId,
    Long workflowId
) {}
