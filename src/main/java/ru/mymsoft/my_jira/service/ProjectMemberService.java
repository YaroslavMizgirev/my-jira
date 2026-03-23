package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateProjectMemberDto;
import ru.mymsoft.my_jira.dto.ProjectMemberDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Group;
import ru.mymsoft.my_jira.model.Project;
import ru.mymsoft.my_jira.model.ProjectMember;
import ru.mymsoft.my_jira.model.Role;
import ru.mymsoft.my_jira.repository.GroupRepository;
import ru.mymsoft.my_jira.repository.ProjectMemberRepository;
import ru.mymsoft.my_jira.repository.ProjectRepository;
import ru.mymsoft.my_jira.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public ProjectMemberDto add(CreateProjectMemberDto request) {
        Project project = projectRepository.findById(request.projectId())
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", request.projectId()));
        Group group = groupRepository.findById(request.groupId())
            .orElseThrow(() -> new ResourceNotFoundException("Group", "id", request.groupId()));
        Role role = roleRepository.findById(request.roleId())
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", request.roleId()));
        if (projectMemberRepository.existsByProject_IdAndGroup_IdAndRole_Id(
                request.projectId(), request.groupId(), request.roleId())) {
            throw new DuplicateResourceException("ProjectMember already exists for project " + request.projectId()
                + ", group " + request.groupId() + ", role " + request.roleId());
        }
        ProjectMember saved = projectMemberRepository.save(
            ProjectMember.builder().project(project).group(group).role(role).build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<ProjectMemberDto> listByProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        return projectMemberRepository.findAllByProject_Id(projectId).stream().map(this::toDto).toList();
    }

    @Transactional
    public void remove(Long projectId, Long groupId, Long roleId) {
        if (!projectMemberRepository.existsByProject_IdAndGroup_IdAndRole_Id(projectId, groupId, roleId)) {
            throw new ResourceNotFoundException("ProjectMember not found for project " + projectId
                + ", group " + groupId + ", role " + roleId);
        }
        projectMemberRepository.deleteByProjectIdAndGroupIdAndRoleId(projectId, groupId, roleId);
    }

    private ProjectMemberDto toDto(ProjectMember e) {
        return new ProjectMemberDto(
            e.getProject().getId(),
            e.getGroup().getId(),
            e.getRole().getId()
        );
    }
}
