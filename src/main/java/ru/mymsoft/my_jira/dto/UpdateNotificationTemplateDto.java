package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateNotificationTemplateDto(
    @NotNull(message = "ID cannot be null")
    Long id,
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name,
    String subjectTemplate,
    @NotBlank(message = "Body template cannot be blank")
    String bodyTemplate,
    @NotBlank(message = "Template type cannot be blank")
    @Size(max = 50, message = "Template type cannot exceed 50 characters")
    String templateType,
    @NotNull(message = "isActive cannot be null")
    Boolean isActive
) {}
