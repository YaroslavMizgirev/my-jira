package ru.mymsoft.my_jira.dto;

import org.hibernate.validator.constraints.EAN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserGroupDto(
    @NotBlank(message = "Id cannot be blank")
    Long userId,

    @NotBlank(message = "Email cannot be blank")
    @EAN(message = "Email must be a valid email address")
    String userEmail,
    
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    String userName,

    @NotBlank(message = "Id cannot be blank")
    Long groupId,

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String groupName,

    String groupDescription,

    @NotBlank(message = "isSystemGroup cannot be blank")
    boolean isSystemGroup) {}