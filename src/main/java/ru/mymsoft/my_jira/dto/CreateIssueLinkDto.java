package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record CreateIssueLinkDto(
    @NotNull Long sourceIssueId,
    @NotNull Long targetIssueId,
    @NotNull Long linkTypeId
) {}
