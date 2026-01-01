package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateIssueTypeDto (
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    String name,

    @Size(max = 255, message = "Icon URL cannot exceed 255 characters")
    String iconUrl,

    @Size(max = 7, message = "Color hex code must be in the format #RRGGBB (7 characters)")
    String colorHexCode) {}