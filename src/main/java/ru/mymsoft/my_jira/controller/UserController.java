package ru.mymsoft.my_jira.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import ru.mymsoft.my_jira.dto.CreateUserDto;
import ru.mymsoft.my_jira.dto.UserDto;
import ru.mymsoft.my_jira.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody CreateUserDto request) {
        return userService.createUser(request);
    }

    //todo: перепроверить в части обязательных полей username, email
    @GetMapping
    public Page<UserDto> listUsers(
            @RequestParam(required = false) String username,
            Pageable pageable
    ) {
        return userService.listUsers(username, pageable);
    }
}