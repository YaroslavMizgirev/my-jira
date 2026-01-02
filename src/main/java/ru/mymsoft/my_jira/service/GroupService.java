package ru.mymsoft.my_jira.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import ru.mymsoft.my_jira.dto.CreateGroupDto;
import ru.mymsoft.my_jira.dto.GroupDto;
import ru.mymsoft.my_jira.dto.UpdateGroupDto;
import ru.mymsoft.my_jira.model.Group;
import ru.mymsoft.my_jira.repository.GroupRepository;

public class GroupService {
    private final GroupRepository groupRepository;
    
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public GroupDto createGroup(CreateGroupDto request) {
        if (groupRepository.existsByNameAndIsSystemGroup(request.name(), request.isSystemGroup())) {
            throw new IllegalArgumentException("Group with this name already exists");
        }

        Group group = Objects.requireNonNull(Group.builder()
                .name(request.name())
                .description(request.description())
                .isSystemGroup(request.isSystemGroup())
                .build(), "Group cannot be null");
        Group savedGroup = groupRepository.save(group);
        return toDto(savedGroup);
    }

    @Transactional(readOnly = true)
    public GroupDto getGroupById(@NonNull Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + id));
        return toDto(group);
    }

    @Transactional(readOnly = true)
    public Page<GroupDto> listGroups(String groupNameFilter, Pageable pageable) {
        String filter = groupNameFilter == null ? "" : groupNameFilter;
        return groupRepository
                .findAllByNameContainsIgnoreCase(filter, pageable)
                .map(this::toDto);
    }

    @Transactional
    public GroupDto updateGroup(@NonNull UpdateGroupDto updatedGroup) {
        Long groupId = Objects.requireNonNull(updatedGroup.id(), "Group ID cannot be null");
        Group existingGroup = Objects.requireNonNull(groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + updatedGroup.id())),
                "Existing Group cannot be null");
        existingGroup.setName(updatedGroup.name());
        existingGroup.setDescription(updatedGroup.description());
        existingGroup.setSystemGroup(updatedGroup.isSystemGroup());

        Group savedGroup = groupRepository.save(existingGroup);
        return toDto(savedGroup);
    }

    @Transactional
    public void deleteGroup(@NonNull GroupDto groupDto) {
        Long groupId = Objects.requireNonNull(groupDto.id(), "Group ID cannot be null");
        Group existingGroup = Objects.requireNonNull(groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupDto.id())),
                "Existing Group cannot be null");
        groupRepository.delete(existingGroup);
    }

    private GroupDto toDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.isSystemGroup()
        );
    }
}