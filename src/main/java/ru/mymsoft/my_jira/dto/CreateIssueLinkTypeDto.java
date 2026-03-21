package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateIssueLinkTypeDto(
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name,
    @NotBlank(message = "Inward name cannot be blank")
    @Size(max = 100, message = "Inward name cannot exceed 100 characters")
    String inwardName
) {}
