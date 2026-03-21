package ru.mymsoft.my_jira.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateUserGroupDto;
import ru.mymsoft.my_jira.dto.UserGroupDto;
import ru.mymsoft.my_jira.service.UserGroupService;

@RestController
@RequestMapping("/api/v1/user-groups")
@RequiredArgsConstructor
@Tag(name = "User Groups", description = "API для управления членством пользователей в группах")
public class UserGroupController {

    private final UserGroupService userGroupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить пользователя в группу")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Пользователь добавлен в группу"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Пользователь или группа не найдены"),
        @ApiResponse(responseCode = "409", description = "Пользователь уже является членом группы")
    })
    public UserGroupDto add(@Valid @RequestBody CreateUserGroupDto request) {
        return userGroupService.add(request);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Получить группы пользователя")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public List<UserGroupDto> listByUser(@PathVariable Long userId) {
        return userGroupService.listByUser(userId);
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "Получить пользователей группы")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Список получен"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public List<UserGroupDto> listByGroup(@PathVariable Long groupId) {
        return userGroupService.listByGroup(groupId);
    }

    @DeleteMapping("/users/{userId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить пользователя из группы")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Пользователь удалён из группы"),
        @ApiResponse(responseCode = "404", description = "Связь не найдена")
    })
    public void remove(@PathVariable Long userId, @PathVariable Long groupId) {
        userGroupService.remove(userId, groupId);
    }
}
