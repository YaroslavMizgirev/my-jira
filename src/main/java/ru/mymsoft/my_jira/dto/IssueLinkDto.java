package ru.mymsoft.my_jira.dto;

import java.time.Instant;

public record IssueLinkDto(
    Long id,
    Long sourceIssueId,
    Long targetIssueId,
    Long linkTypeId,
    Instant createdAt
) {}
