package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProjectDto(
    @NotBlank @Size(max = 255) String name,
    @NotBlank @Size(max = 50) String key,
    String description,
    Long leadId,
    Long defaultWorkflowId
) {}
