package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateWorkflowTransitionDto;
import ru.mymsoft.my_jira.dto.UpdateWorkflowTransitionDto;
import ru.mymsoft.my_jira.dto.WorkflowTransitionDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.IssueStatus;
import ru.mymsoft.my_jira.model.Workflow;
import ru.mymsoft.my_jira.model.WorkflowTransition;
import ru.mymsoft.my_jira.repository.IssueStatusRepository;
import ru.mymsoft.my_jira.repository.WorkflowRepository;
import ru.mymsoft.my_jira.repository.WorkflowTransitionRepository;

@Service
@RequiredArgsConstructor
public class WorkflowTransitionService {

    private final WorkflowTransitionRepository workflowTransitionRepository;
    private final WorkflowRepository workflowRepository;
    private final IssueStatusRepository issueStatusRepository;

    @Transactional
    public WorkflowTransitionDto create(CreateWorkflowTransitionDto request) {
        Workflow workflow = workflowRepository.findById(request.workflowId())
            .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", request.workflowId()));
        IssueStatus fromStatus = issueStatusRepository.findById(request.fromStatusId())
            .orElseThrow(() -> new ResourceNotFoundException("IssueStatus", "id", request.fromStatusId()));
        IssueStatus toStatus = issueStatusRepository.findById(request.toStatusId())
            .orElseThrow(() -> new ResourceNotFoundException("IssueStatus", "id", request.toStatusId()));
        if (workflowTransitionRepository.existsByWorkflowIdAndName(request.workflowId(), request.name())) {
            throw new DuplicateResourceException("WorkflowTransition", "name", request.name());
        }
        if (workflowTransitionRepository.existsByWorkflowIdAndFromStatusIdAndToStatusId(
                request.workflowId(), request.fromStatusId(), request.toStatusId())) {
            throw new DuplicateResourceException("WorkflowTransition already exists for this workflow/from/to combination");
        }
        WorkflowTransition saved = workflowTransitionRepository.save(
            WorkflowTransition.builder()
                .workflow(workflow)
                .name(request.name())
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public WorkflowTransitionDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<WorkflowTransitionDto> listByWorkflow(Long workflowId) {
        if (!workflowRepository.existsById(workflowId)) {
            throw new ResourceNotFoundException("Workflow", "id", workflowId);
        }
        return workflowTransitionRepository.findAllByWorkflowId(workflowId).stream().map(this::toDto).toList();
    }

    @Transactional
    public WorkflowTransitionDto update(Long id, UpdateWorkflowTransitionDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        WorkflowTransition existing = findOrThrow(id);
        IssueStatus fromStatus = issueStatusRepository.findById(request.fromStatusId())
            .orElseThrow(() -> new ResourceNotFoundException("IssueStatus", "id", request.fromStatusId()));
        IssueStatus toStatus = issueStatusRepository.findById(request.toStatusId())
            .orElseThrow(() -> new ResourceNotFoundException("IssueStatus", "id", request.toStatusId()));
        if (!existing.getName().equals(request.name()) &&
                workflowTransitionRepository.existsByWorkflowIdAndName(existing.getWorkflow().getId(), request.name())) {
            throw new DuplicateResourceException("WorkflowTransition", "name", request.name());
        }
        existing.setName(request.name());
        existing.setFromStatus(fromStatus);
        existing.setToStatus(toStatus);
        return toDto(workflowTransitionRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!workflowTransitionRepository.existsById(id)) {
            throw new ResourceNotFoundException("WorkflowTransition", "id", id);
        }
        workflowTransitionRepository.deleteById(id);
    }

    private WorkflowTransition findOrThrow(Long id) {
        return workflowTransitionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("WorkflowTransition", "id", id));
    }

    private WorkflowTransitionDto toDto(WorkflowTransition e) {
        return new WorkflowTransitionDto(
            e.getId(),
            e.getWorkflow().getId(),
            e.getName(),
            e.getFromStatus().getId(),
            e.getToStatus().getId()
        );
    }
}
