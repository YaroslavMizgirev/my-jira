package ru.mymsoft.my_jira.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto (
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    String email,

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters")
    String username,

   @NotBlank(message = "Password cannot be blank")
//    @Size(min = 8, message = "Password must be at least 8 characters long")
    String password) {}