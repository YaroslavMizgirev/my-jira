package ru.mymsoft.my_jira.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateGroupDto;
import ru.mymsoft.my_jira.dto.GroupDto;
import ru.mymsoft.my_jira.service.GroupService;

@RestController
@RequestMapping("/api/v1/groups")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto createGroup(@RequestBody CreateGroupDto request) {
        return groupService.createGroup(request);
    }

    // todo: перепроверить в части обязательных полей name, isSystemGroup
    @GetMapping
    public Page<GroupDto> listGroups(
            @RequestBody(required = false) String groupName,
            Pageable pageable
    ) {
        return groupService.listGroups(groupName, pageable);
    }
}
