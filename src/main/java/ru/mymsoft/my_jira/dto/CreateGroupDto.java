package ru.mymsoft.my_jira.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для создания новой группы")
public record CreateGroupDto(
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Schema(description = "Название группы", example = "Developers", nullable = false)
    String name,
    
    @Schema(description = "Описание группы", example = "Group for all developer team members", nullable = true)
    String description,
    
    @NotNull(message = "isSystemGroup cannot be null")
    @Schema(description = "Флаг системной группы", example = "false", nullable = false)
    Boolean isSystemGroup) {}