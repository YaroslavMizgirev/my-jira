package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePriorityDto(
    @NotNull(message = "Level cannot be null")
    Integer level,
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    String name,
    String iconUrl,
    @Size(max = 7, message = "Color hex code must be in format #RRGGBB")
    String colorHexCode
) {}
