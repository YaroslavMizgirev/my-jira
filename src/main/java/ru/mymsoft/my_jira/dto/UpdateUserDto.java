package ru.mymsoft.my_jira.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для обновления пользователя (используется для PUT и PATCH запросов)")
public record UpdateUserDto (
    @NotNull(message = "Id is required", groups = {FullUpdate.class, PartialUpdate.class})
    @Schema(description = "Уникальный идентификатор пользователя", example = "1", nullable = false)
    Long id,

    @NotNull(message = "Email is required for full update", groups = FullUpdate.class)
    @Email(message = "Email must be a valid email address", groups = FullUpdate.class)
    @Size(min = 6, max = 255, message = "Email must be between 6 and 255 characters")
    @Null(message = "Email should not be provided for partial update", groups = PartialUpdate.class)
    @Schema(description = "Новая электронная почта пользователя (опционально)", 
            example = "new.email@example.com", 
            nullable = true)
    String email,
    
    @NotNull(message = "Username is required for full update", groups = FullUpdate.class)
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    @Null(message = "Username should not be provided for partial update", groups = PartialUpdate.class)
    @Schema(description = "Новое уникальное имя пользователя (опционально)", 
            example = "new_username", 
            nullable = true)
    String username,

    @NotNull(message = "New password is required for full update", groups = FullUpdate.class)
    @Null(message = "New password should not be provided for partial update", groups = PartialUpdate.class)
    @Schema(description = "Новый пароль пользователя (опционально)", 
            example = "NewP@ssw0rd!", 
            nullable = true)
    String newPassword,

    @NotNull(message = "Current password is required", groups = {FullUpdate.class, PartialUpdate.class})
    @Schema(description = "Текущий пароль для подтверждения операции", 
            example = "currentP@ssw0rd!", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    String currentPassword) {
        public interface FullUpdate {}          // Для PUT запросов
        public interface PartialUpdate {}       // Для PATCH запросов
    }