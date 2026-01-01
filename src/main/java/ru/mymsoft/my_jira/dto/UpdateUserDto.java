package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserDto (
    @NotBlank(message = "Id cannot be blank")
    Long id,

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    String email,
    
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    String username,

    // Пароль может быть необязательным для обновления
    // @Size(min = 8, message = "New password must be at least 8 characters long")
    String newPassword,

    // Требуется старый пароль для подтверждения обновления
    @NotBlank(message = "Type password for update")
    String oldPassword) {}