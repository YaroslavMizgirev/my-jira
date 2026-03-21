package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateIssueDto(
    @NotBlank @Size(max = 255) String title,
    String description,
    Long assigneeId,
    Long statusId,
    Long issueTypeId,
    Long priorityId,
    Long workflowId
) {}
