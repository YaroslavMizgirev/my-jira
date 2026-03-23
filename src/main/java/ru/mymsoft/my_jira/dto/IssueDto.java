package ru.mymsoft.my_jira.dto;

import java.time.Instant;

public record IssueDto(
    Long id,
    String title,
    String key,
    String description,
    Long projectId,
    Long reporterId,
    Long assigneeId,
    Long statusId,
    Long issueTypeId,
    Long priorityId,
    Long workflowId,
    Instant createdAt,
    Instant updatedAt
) {}
