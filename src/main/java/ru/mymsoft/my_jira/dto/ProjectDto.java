package ru.mymsoft.my_jira.dto;

import java.time.Instant;

public record ProjectDto(
    Long id,
    String name,
    String key,
    String description,
    Long leadId,
    Long defaultWorkflowId,
    Instant createdAt,
    Instant updatedAt
) {}
