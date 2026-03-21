package ru.mymsoft.my_jira.dto;

import java.time.Instant;

public record AttachmentDto(
    Long id,
    Long issueId,
    Long uploaderId,
    String fileName,
    Long fileTypeId,
    Long fileSizeBytes,
    String storagePath,
    String description,
    Instant updatedAt
) {}
