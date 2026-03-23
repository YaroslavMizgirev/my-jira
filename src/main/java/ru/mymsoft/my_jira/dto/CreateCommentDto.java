package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentDto(
    @NotNull Long issueId,
    @NotNull Long authorId,
    @NotBlank String content
) {}
