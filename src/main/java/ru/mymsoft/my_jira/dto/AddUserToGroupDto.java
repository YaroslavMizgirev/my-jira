package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;

public record AddUserToGroupDto (
    @NotBlank(message = "User ID is required")
    Long userId,

    @NotBlank(message = "Group ID is required")
    Long groupId) {}