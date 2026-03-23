package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateRoleDto(
    @NotNull(message = "ID cannot be null")
    Long id,
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name,
    String description,
    @NotNull(message = "isSystemRole cannot be null")
    Boolean isSystemRole
) {}
