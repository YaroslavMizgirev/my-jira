package ru.mymsoft.my_jira.dto;

public record IssueWatcherDto(
    Long issueId,
    Long userId
) {}
