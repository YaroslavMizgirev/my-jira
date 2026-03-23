package ru.mymsoft.my_jira.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для создания нового типа действия")
public record CreateActionTypeDto(
    @NotNull(message = "Название типа действия не может быть NULL")
    @NotBlank(message = "Название типа действия не может быть пустым")
    @Size(min = 2, max = 100, message = "Название типа действия должно быть от 2 до 100 символов")
    @Schema(description = "Название типа действия", example = "Задача создана")
    String name) {
}