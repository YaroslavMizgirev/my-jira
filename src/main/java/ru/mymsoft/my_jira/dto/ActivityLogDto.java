package ru.mymsoft.my_jira.dto;

import java.time.Instant;

public record ActivityLogDto(
    Long id,
    Long issueId,
    Long userId,
    Long actionTypeId,
    String fieldName,
    String oldValue,
    String newValue,
    Instant createdAt
) {}
