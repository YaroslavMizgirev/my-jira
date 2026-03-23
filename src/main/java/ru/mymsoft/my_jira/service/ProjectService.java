package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateProjectDto;
import ru.mymsoft.my_jira.dto.ProjectDto;
import ru.mymsoft.my_jira.dto.UpdateProjectDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Project;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.model.Workflow;
import ru.mymsoft.my_jira.repository.ProjectRepository;
import ru.mymsoft.my_jira.repository.UserRepository;
import ru.mymsoft.my_jira.repository.WorkflowRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final WorkflowRepository workflowRepository;

    @Transactional(readOnly = true)
    public Page<ProjectDto> list(String filter, Pageable pageable) {
        String f = (filter == null) ? "" : filter;
        return projectRepository.findAllByNameContainingIgnoreCase(f, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public ProjectDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional
    public ProjectDto create(CreateProjectDto request) {
        if (projectRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Project with name '" + request.name() + "' already exists");
        }
        if (projectRepository.existsByKey(request.key())) {
            throw new DuplicateResourceException("Project with key '" + request.key() + "' already exists");
        }
        User lead = resolveUser(request.leadId());
        Workflow workflow = resolveWorkflow(request.defaultWorkflowId());
        Project saved = projectRepository.save(Project.builder()
            .name(request.name())
            .key(request.key())
            .description(request.description())
            .lead(lead)
            .defaultWorkflow(workflow)
            .build());
        return toDto(saved);
    }

    @Transactional
    public ProjectDto update(Long id, UpdateProjectDto request) {
        Project existing = findOrThrow(id);
        if (projectRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new DuplicateResourceException("Project with name '" + request.name() + "' already exists");
        }
        if (projectRepository.existsByKeyAndIdNot(request.key(), id)) {
            throw new DuplicateResourceException("Project with key '" + request.key() + "' already exists");
        }
        existing.setName(request.name());
        existing.setKey(request.key());
        existing.setDescription(request.description());
        existing.setLead(resolveUser(request.leadId()));
        existing.setDefaultWorkflow(resolveWorkflow(request.defaultWorkflowId()));
        return toDto(projectRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project", "id", id);
        }
        projectRepository.deleteById(id);
    }

    private Project findOrThrow(Long id) {
        return projectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
    }

    private User resolveUser(Long userId) {
        if (userId == null) return null;
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    private Workflow resolveWorkflow(Long workflowId) {
        if (workflowId == null) return null;
        return workflowRepository.findById(workflowId)
            .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", workflowId));
    }

    private ProjectDto toDto(Project p) {
        return new ProjectDto(
            p.getId(),
            p.getName(),
            p.getKey(),
            p.getDescription(),
            p.getLead() != null ? p.getLead().getId() : null,
            p.getDefaultWorkflow() != null ? p.getDefaultWorkflow().getId() : null,
            p.getCreatedAt(),
            p.getUpdatedAt()
        );
    }
}
