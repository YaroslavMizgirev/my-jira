package ru.mymsoft.my_jira.dto;

public record IssueTypeDto (
    Long id,
    String name,
    String iconUrl,
    String colorHexCode) {}