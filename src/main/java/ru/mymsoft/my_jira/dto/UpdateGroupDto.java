package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateGroupDto(
    @NotBlank(message = "Id cannot be blank")
    Long id,

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String name,
    
    String description,
    
    @NotBlank(message = "isSystemGroup cannot be blank")
    boolean isSystemGroup) {}