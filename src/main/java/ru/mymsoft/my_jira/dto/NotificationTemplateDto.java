package ru.mymsoft.my_jira.dto;

import java.time.Instant;

public record NotificationTemplateDto(
    Long id,
    String name,
    String subjectTemplate,
    String bodyTemplate,
    String templateType,
    Boolean isActive,
    Instant createdAt,
    Instant updatedAt
) {}
