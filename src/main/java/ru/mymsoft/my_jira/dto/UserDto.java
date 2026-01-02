package ru.mymsoft.my_jira.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object for user information")
public record UserDto (
    @NotNull(message = "Id cannot be blank")
    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    Long id,
    
    @Email(message = "Email must be a valid email address")
    @Size(min = 3, max = 255, message = "Email must be between 3 and 255 characters")
    @Schema(description = "Электронная почта пользователя", example = "user@example.com")
    String email,

    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    @Schema(description = "Уникальное имя пользователя", example = "john_doe")
    String username) {}