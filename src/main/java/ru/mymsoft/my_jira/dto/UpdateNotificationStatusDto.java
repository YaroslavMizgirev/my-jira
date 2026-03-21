package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateNotificationStatusDto(
    @NotNull(message = "ID cannot be null")
    Long id,
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 20, message = "Name cannot exceed 20 characters")
    String name
) {}
