package ru.mymsoft.my_jira.dto;

public record ProjectMemberDto(
    Long projectId,
    Long groupId,
    Long roleId
) {}
