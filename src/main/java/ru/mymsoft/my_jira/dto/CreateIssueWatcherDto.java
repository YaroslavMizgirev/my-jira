package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record CreateIssueWatcherDto(
    @NotNull Long issueId,
    @NotNull Long userId
) {}
