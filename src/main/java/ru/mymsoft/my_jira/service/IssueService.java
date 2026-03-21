package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateIssueDto;
import ru.mymsoft.my_jira.dto.IssueDto;
import ru.mymsoft.my_jira.dto.UpdateIssueDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Issue;
import ru.mymsoft.my_jira.model.IssueStatus;
import ru.mymsoft.my_jira.model.IssueType;
import ru.mymsoft.my_jira.model.Priority;
import ru.mymsoft.my_jira.model.Project;
import ru.mymsoft.my_jira.model.User;
import ru.mymsoft.my_jira.model.Workflow;
import ru.mymsoft.my_jira.repository.IssueRepository;
import ru.mymsoft.my_jira.repository.IssueStatusRepository;
import ru.mymsoft.my_jira.repository.IssueTypeRepository;
import ru.mymsoft.my_jira.repository.PriorityRepository;
import ru.mymsoft.my_jira.repository.ProjectRepository;
import ru.mymsoft.my_jira.repository.UserRepository;
import ru.mymsoft.my_jira.repository.WorkflowRepository;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final IssueStatusRepository issueStatusRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final PriorityRepository priorityRepository;
    private final WorkflowRepository workflowRepository;

    @Transactional(readOnly = true)
    public Page<IssueDto> list(String filter, Pageable pageable) {
        String f = (filter == null) ? "" : filter;
        return issueRepository.findAllByTitleContainingIgnoreCase(f, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Page<IssueDto> listByProject(Long projectId, Pageable pageable) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        return issueRepository.findAllByProject_Id(projectId, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public IssueDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional
    public IssueDto create(CreateIssueDto request) {
        if (issueRepository.existsByKey(request.key())) {
            throw new DuplicateResourceException("Issue with key '" + request.key() + "' already exists");
        }
        Project project = projectRepository.findById(request.projectId())
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", request.projectId()));
        User reporter = userRepository.findById(request.reporterId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.reporterId()));

        Issue saved = issueRepository.save(Issue.builder()
            .title(request.title())
            .key(request.key())
            .description(request.description())
            .project(project)
            .reporter(reporter)
            .assignee(resolveUser(request.assigneeId()))
            .status(resolveStatus(request.statusId()))
            .issueType(resolveIssueType(request.issueTypeId()))
            .priority(resolvePriority(request.priorityId()))
            .workflow(resolveWorkflow(request.workflowId()))
            .build());
        return toDto(saved);
    }

    @Transactional
    public IssueDto update(Long id, UpdateIssueDto request) {
        Issue existing = findOrThrow(id);
        existing.setTitle(request.title());
        existing.setDescription(request.description());
        existing.setAssignee(resolveUser(request.assigneeId()));
        existing.setStatus(resolveStatus(request.statusId()));
        existing.setIssueType(resolveIssueType(request.issueTypeId()));
        existing.setPriority(resolvePriority(request.priorityId()));
        existing.setWorkflow(resolveWorkflow(request.workflowId()));
        return toDto(issueRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Issue", "id", id);
        }
        issueRepository.deleteById(id);
    }

    private Issue findOrThrow(Long id) {
        return issueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Issue", "id", id));
    }

    private User resolveUser(Long userId) {
        if (userId == null) return null;
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    private IssueStatus resolveStatus(Long statusId) {
        if (statusId == null) return null;
        return issueStatusRepository.findById(statusId)
            .orElseThrow(() -> new ResourceNotFoundException("IssueStatus", "id", statusId));
    }

    private IssueType resolveIssueType(Long typeId) {
        if (typeId == null) return null;
        return issueTypeRepository.findById(typeId)
            .orElseThrow(() -> new ResourceNotFoundException("IssueType", "id", typeId));
    }

    private Priority resolvePriority(Long priorityId) {
        if (priorityId == null) return null;
        return priorityRepository.findById(priorityId)
            .orElseThrow(() -> new ResourceNotFoundException("Priority", "id", priorityId));
    }

    private Workflow resolveWorkflow(Long workflowId) {
        if (workflowId == null) return null;
        return workflowRepository.findById(workflowId)
            .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", workflowId));
    }

    private IssueDto toDto(Issue i) {
        return new IssueDto(
            i.getId(),
            i.getTitle(),
            i.getKey(),
            i.getDescription(),
            i.getProject().getId(),
            i.getReporter().getId(),
            i.getAssignee() != null ? i.getAssignee().getId() : null,
            i.getStatus() != null ? i.getStatus().getId() : null,
            i.getIssueType() != null ? i.getIssueType().getId() : null,
            i.getPriority() != null ? i.getPriority().getId() : null,
            i.getWorkflow() != null ? i.getWorkflow().getId() : null,
            i.getCreatedAt(),
            i.getUpdatedAt()
        );
    }
}
