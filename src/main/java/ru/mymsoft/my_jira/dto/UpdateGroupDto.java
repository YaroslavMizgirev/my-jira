package ru.mymsoft.my_jira.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для обновления информации о группе")
public record UpdateGroupDto(
    @NotNull(message = "Id is required", groups = {FullUpdate.class, PartialUpdate.class})
    @Schema(description = "Уникальный идентификатор группы", example = "1", nullable = false)
    Long id,

    @NotNull(message = "Name cannot be null", groups = FullUpdate.class)
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Null(message = "Name should not be provided for partial update", groups = PartialUpdate.class)
    @Schema(description = "Новое название группы (опционально)", example = "Developers", nullable = true)
    String name,

    @NotNull(message = "Description cannot be null", groups = FullUpdate.class)
    @Null(message = "Description should not be provided for partial update", groups = PartialUpdate.class)
    @Schema(description = "Новое описание группы (опционально)", example = "Group for all developer team members", nullable = true)
    String description,

    @NotNull(message = "isSystemGroup cannot be null", groups = FullUpdate.class)
    @Null(message = "isSystemGroup should not be provided for partial update", groups = PartialUpdate.class)
    @Schema(description = "Флаг системной группы (опционально)", example = "false", nullable = true)
    Boolean isSystemGroup) {
        public interface FullUpdate {}      // Для PUT запросов
        public interface PartialUpdate {}   // Для PATCH запросов
    }