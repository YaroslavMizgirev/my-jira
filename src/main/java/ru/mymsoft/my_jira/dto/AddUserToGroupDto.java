package ru.mymsoft.my_jira.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO для добавления пользователя в группу")
public record AddUserToGroupDto (
    @NotNull(message = "User ID is required")
    @Schema(description = "ID пользователя", example = "1")
    Long userId,

    @NotNull(message = "Group ID is required")
    @Schema(description = "ID группы", example = "2")
    Long groupId) {}