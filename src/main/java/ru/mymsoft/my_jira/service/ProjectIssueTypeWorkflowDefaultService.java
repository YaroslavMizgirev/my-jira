package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateProjectWorkflowDefaultDto;
import ru.mymsoft.my_jira.dto.ProjectWorkflowDefaultDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.IssueType;
import ru.mymsoft.my_jira.model.Project;
import ru.mymsoft.my_jira.model.ProjectIssueTypeWorkflowDefault;
import ru.mymsoft.my_jira.model.Workflow;
import ru.mymsoft.my_jira.repository.IssueTypeRepository;
import ru.mymsoft.my_jira.repository.ProjectIssueTypeWorkflowDefaultRepository;
import ru.mymsoft.my_jira.repository.ProjectRepository;
import ru.mymsoft.my_jira.repository.WorkflowRepository;

@Service
@RequiredArgsConstructor
public class ProjectIssueTypeWorkflowDefaultService {

    private final ProjectIssueTypeWorkflowDefaultRepository repository;
    private final ProjectRepository projectRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final WorkflowRepository workflowRepository;

    @Transactional
    public ProjectWorkflowDefaultDto assign(CreateProjectWorkflowDefaultDto request) {
        Project project = projectRepository.findById(request.projectId())
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", request.projectId()));
        IssueType issueType = issueTypeRepository.findById(request.issueTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("IssueType", "id", request.issueTypeId()));
        Workflow workflow = workflowRepository.findById(request.workflowId())
            .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", request.workflowId()));
        if (repository.existsByProject_IdAndIssueType_IdAndWorkflow_Id(
                request.projectId(), request.issueTypeId(), request.workflowId())) {
            throw new DuplicateResourceException("WorkflowDefault already exists for project " + request.projectId()
                + ", issueType " + request.issueTypeId() + ", workflow " + request.workflowId());
        }
        ProjectIssueTypeWorkflowDefault saved = repository.save(
            ProjectIssueTypeWorkflowDefault.builder()
                .project(project)
                .issueType(issueType)
                .workflow(workflow)
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<ProjectWorkflowDefaultDto> listByProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project", "id", projectId);
        }
        return repository.findAllByProject_Id(projectId).stream().map(this::toDto).toList();
    }

    @Transactional
    public void remove(Long projectId, Long issueTypeId, Long workflowId) {
        if (!repository.existsByProject_IdAndIssueType_IdAndWorkflow_Id(projectId, issueTypeId, workflowId)) {
            throw new ResourceNotFoundException("WorkflowDefault not found for project " + projectId
                + ", issueType " + issueTypeId + ", workflow " + workflowId);
        }
        repository.deleteByProjectIdAndIssueTypeIdAndWorkflowId(projectId, issueTypeId, workflowId);
    }

    private ProjectWorkflowDefaultDto toDto(ProjectIssueTypeWorkflowDefault e) {
        return new ProjectWorkflowDefaultDto(
            e.getProject().getId(),
            e.getIssueType().getId(),
            e.getWorkflow().getId()
        );
    }
}
