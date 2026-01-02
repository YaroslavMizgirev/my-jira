package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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

        Group group = Group.builder()
                .name(request.name())
                .description(request.description())
                .isSystemGroup(request.isSystemGroup())
                .build();
        Group savedGroup = groupRepository.save(group);
        return toDto(savedGroup);
    }

    @Transactional(readOnly = true)
    public GroupDto getGroupById(Long id) {
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
    public GroupDto updateGroup(UpdateGroupDto updatedGroup) {
        Group existingGroup = groupRepository.findById(updatedGroup.id())
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + updatedGroup.id()));
        existingGroup.setName(updatedGroup.name());
        existingGroup.setDescription(updatedGroup.description());
        existingGroup.setSystemGroup(updatedGroup.isSystemGroup());

        Group savedGroup = groupRepository.save(existingGroup);
        return toDto(savedGroup);
    }

    @Transactional
    public void deleteGroup(GroupDto groupDto) {
        Group existingGroup = groupRepository.findById(groupDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupDto.id()));
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