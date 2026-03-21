package ru.mymsoft.my_jira.dto;

import java.time.Instant;

public record CommentDto(
    Long id,
    Long issueId,
    Long authorId,
    String content,
    Instant createdAt,
    Instant updatedAt
) {}
