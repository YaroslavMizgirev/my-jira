package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentDto(
    @NotBlank String content
) {}
