package ru.mymsoft.my_jira.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для представления типа действия")
public record ActionTypeDto(
    @NotNull(message = "Идентификатор типа действия не может быть null")
    @Schema(description = "Уникальный идентификатор типа действия", example = "1", nullable = false)
    Long id,

    @NotNull(message = "Название типа действия не может быть null")
    @Size(min = 1, max = 100, message = "Название типа действия должно быть от 1 до 100 символов")
    @Schema(description = "Название типа действия", example = "STATUS_CHANGE", nullable = false)
    String name) {}