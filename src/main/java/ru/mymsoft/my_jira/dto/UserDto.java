package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto (
    @NotBlank(message = "Id cannot be blank")
    Long id,
    
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 3, max = 255, message = "Email must be between 3 and 255 characters")
    String email,

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    String username) {}