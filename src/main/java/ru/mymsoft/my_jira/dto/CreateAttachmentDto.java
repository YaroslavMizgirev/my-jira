package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateAttachmentDto(
    @NotNull Long issueId,
    @NotNull Long uploaderId,
    @NotBlank @Size(max = 255) String fileName,
    @NotNull Long fileTypeId,
    @NotNull @Positive Long fileSizeBytes,
    @NotBlank @Size(max = 4096) String storagePath,
    String description
) {}
