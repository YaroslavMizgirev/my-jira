package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull Long id,
        String email,
        String username,
        String displayName,
        String avatarUrl,
        String oauthProvider
) {}
