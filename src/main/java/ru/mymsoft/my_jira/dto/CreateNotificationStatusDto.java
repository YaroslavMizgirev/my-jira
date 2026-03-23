package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateNotificationStatusDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 20, message = "Name cannot exceed 20 characters")
    String name
) {}
