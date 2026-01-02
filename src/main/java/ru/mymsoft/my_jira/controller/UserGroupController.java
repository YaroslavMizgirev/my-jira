package ru.mymsoft.my_jira.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.AddUserToGroupDto;
import ru.mymsoft.my_jira.dto.UserGroupDto;
import ru.mymsoft.my_jira.service.UserGroupService;

@RestController
@RequestMapping("/api/v1/user-groups")
@RequiredArgsConstructor
@Tag(name = "User Groups", description = "API для управления группами пользователей")
public class UserGroupController {
    private final UserGroupService userGroupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление пользователя в группу")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Пользователь успешно добавлен в группу"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Пользователь или группа не найдены")
    })
    public UserGroupDto addUserToGroup(@Valid @RequestBody AddUserToGroupDto request) {
        return userGroupService.addUserToGroup(request);
    }
}