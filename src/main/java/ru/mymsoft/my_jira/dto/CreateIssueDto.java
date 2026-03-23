package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIssueDto(
    @NotBlank @Size(max = 255) String title,
    @NotBlank @Size(max = 50) String key,
    String description,
    @NotNull Long projectId,
    @NotNull Long reporterId,
    Long assigneeId,
    Long statusId,
    Long issueTypeId,
    Long priorityId,
    Long workflowId
) {}
