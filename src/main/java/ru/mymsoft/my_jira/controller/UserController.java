package ru.mymsoft.my_jira.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import ru.mymsoft.my_jira.dto.CreateUserDto;
import ru.mymsoft.my_jira.dto.UpdateUserDto;
import ru.mymsoft.my_jira.dto.UserDto;
import ru.mymsoft.my_jira.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API для управления пользователями")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
        @ApiResponse(responseCode = "400", description = "Неверные данные пользователя"),
        @ApiResponse(responseCode = "409", description = "Пользователь с таким email или username уже существует")
    })
    public UserDto createUser(@Valid @RequestBody CreateUserDto request) {
        return userService.createUser(request);
    }

    @GetMapping
    @Operation(summary = "Получение списка пользователей с фильтрацией")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Список пользователей получен успешно")
    })
    public Page<UserDto> listUsers(
            @Parameter(description = "Фильтр по имени пользователя (опционально)")
            @RequestParam(required = false) String username,
            Pageable pageable
    ) {
        return userService.listUsers(username, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение пользователя по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь найден"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserDto getUserById(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Получение пользователя по username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь найден"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserDto getUserByUsername(
            @Parameter(description = "Username пользователя", example = "john_doe")
            @PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Получение пользователя по email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь найден"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserDto getUserByEmail(
            @Parameter(description = "Email пользователя", example = "john_doe@example.com")
            @PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Полное обновление пользователя (PUT)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
        @ApiResponse(responseCode = "400", description = "Неверные данные для обновления"),
        @ApiResponse(responseCode = "403", description = "Нет прав для обновления этого пользователя"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserDto updateUserFull(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id,
            @Validated(UpdateUserDto.FullUpdate.class) @RequestBody UpdateUserDto request) {
        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Частичное обновление пользователя (PATCH)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
        @ApiResponse(responseCode = "400", description = "Неверные данные для обновления"),
        @ApiResponse(responseCode = "403", description = "Нет прав для обновления этого пользователя"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public UserDto updateUserPartial(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id,
            @Validated(UpdateUserDto.PartialUpdate.class) @RequestBody UpdateUserDto request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
        @ApiResponse(responseCode = "403", description = "Нет прав для удаления этого пользователя"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public void deleteUser(
            @Parameter(description = "ID пользователя", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
    }
}