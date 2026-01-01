package ru.mymsoft.my_jira.service;

import org.springframework.transaction.annotation.Transactional;

import ru.mymsoft.my_jira.dto.UserGroupDto;
import ru.mymsoft.my_jira.model.Group;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.model.UserGroup;
import ru.mymsoft.my_jira.repository.GroupRepository;
import ru.mymsoft.my_jira.repository.UserGroupRepository;
import ru.mymsoft.my_jira.repository.UserRepository;

public class UserGroupService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    
    public UserGroupService(UserRepository userRepository,
                            GroupRepository groupRepository,
                            UserGroupRepository userGroupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userGroupRepository = userGroupRepository;
    }

    @Transactional
    public UserGroupDto addUserToGroup(Long userId, Long groupId) {
        if (userId == null || groupId == null) {
            throw new IllegalArgumentException("User ID and Group ID must be provided");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupId));

        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .build();
        @SuppressWarnings("null")
        UserGroup ugr = userGroupRepository.save(userGroup);
        return toDto(ugr);
    }

    @SuppressWarnings("null")
    @Transactional
    public void removeUserFromGroup(Long userId, Long groupId) {
        UserGroup userGroup = userGroupRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new IllegalArgumentException("User is not in the specified group"));
        userGroupRepository.delete(userGroup);
    }

    private UserGroupDto toDto(UserGroup userGroup) {
        User user = userGroup.getUser();
        Group group = userGroup.getGroup();
        return new UserGroupDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.isSystemGroup()
        );
    }
}