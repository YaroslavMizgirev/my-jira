package ru.mymsoft.my_jira.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import ru.mymsoft.my_jira.dto.CreateGroupDto;
import ru.mymsoft.my_jira.dto.GroupDto;
import ru.mymsoft.my_jira.dto.UpdateGroupDto;
import ru.mymsoft.my_jira.service.GroupService;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Groups", description = "API для управления группами")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание новой группы")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Группа создана"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "409", description = "Группа с таким именем уже существует")
    })
    public GroupDto createGroup(@Valid @RequestBody CreateGroupDto request) {
        return groupService.createGroup(request);
    }

    @GetMapping
    @Operation(summary = "Список групп с фильтрацией по имени")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Список получен"))
    public Page<GroupDto> listGroups(
        @Parameter(description = "Фильтр по части имени") @RequestParam(required = false) String groupName,
        Pageable pageable
    ) {
        return groupService.listGroups(groupName, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение группы по ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Группа найдена"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public GroupDto getById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление группы")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Группа обновлена"),
        @ApiResponse(responseCode = "400", description = "Неверные данные"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public GroupDto update(@PathVariable Long id, @Valid @RequestBody UpdateGroupDto request) {
        return groupService.updateGroup(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление группы")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Группа удалена"),
        @ApiResponse(responseCode = "404", description = "Группа не найдена")
    })
    public void delete(@PathVariable Long id) {
        GroupDto groupDto = groupService.getGroupById(id);
        groupService.deleteGroup(groupDto);
    }
}
