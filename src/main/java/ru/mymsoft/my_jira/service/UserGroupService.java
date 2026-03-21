package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateUserGroupDto;
import ru.mymsoft.my_jira.dto.UserGroupDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Group;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.model.UserGroup;
import ru.mymsoft.my_jira.repository.GroupRepository;
import ru.mymsoft.my_jira.repository.UserGroupRepository;
import ru.mymsoft.my_jira.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public UserGroupDto add(CreateUserGroupDto request) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.userId()));
        Group group = groupRepository.findById(request.groupId())
            .orElseThrow(() -> new ResourceNotFoundException("Group", "id", request.groupId()));
        if (userGroupRepository.existsByUser_IdAndGroup_Id(request.userId(), request.groupId())) {
            throw new DuplicateResourceException("User " + request.userId() + " is already a member of group " + request.groupId());
        }
        UserGroup saved = userGroupRepository.save(UserGroup.builder().user(user).group(group).build());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<UserGroupDto> listByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return userGroupRepository.findAllByUser_Id(userId, Pageable.unpaged())
            .stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<UserGroupDto> listByGroup(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group", "id", groupId);
        }
        return userGroupRepository.findAllByGroup_Id(groupId, Pageable.unpaged())
            .stream().map(this::toDto).toList();
    }

    @Transactional
    public void remove(Long userId, Long groupId) {
        if (!userGroupRepository.existsByUser_IdAndGroup_Id(userId, groupId)) {
            throw new ResourceNotFoundException("UserGroup not found for user " + userId + " and group " + groupId);
        }
        userGroupRepository.deleteByUser_IdAndGroup_Id(userId, groupId);
    }

    private UserGroupDto toDto(UserGroup e) {
        return new UserGroupDto(e.getUser().getId(), e.getGroup().getId());
    }
}
