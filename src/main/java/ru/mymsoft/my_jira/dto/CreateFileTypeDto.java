package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateFileTypeDto(
    @NotBlank(message = "Extension cannot be blank")
    @Size(max = 20, message = "Extension cannot exceed 20 characters")
    String extension,
    @NotBlank(message = "MIME type cannot be blank")
    @Size(max = 100, message = "MIME type cannot exceed 100 characters")
    String mimeType
) {}
