package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateWorkflowStatusDto;
import ru.mymsoft.my_jira.dto.WorkflowStatusDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.IssueStatus;
import ru.mymsoft.my_jira.model.Workflow;
import ru.mymsoft.my_jira.model.WorkflowStatus;
import ru.mymsoft.my_jira.model.WorkflowStatus.WorkflowStatusId;
import ru.mymsoft.my_jira.repository.IssueStatusRepository;
import ru.mymsoft.my_jira.repository.WorkflowRepository;
import ru.mymsoft.my_jira.repository.WorkflowStatusRepository;

@Service
@RequiredArgsConstructor
public class WorkflowStatusService {

    private final WorkflowStatusRepository workflowStatusRepository;
    private final WorkflowRepository workflowRepository;
    private final IssueStatusRepository issueStatusRepository;

    @Transactional
    public WorkflowStatusDto add(CreateWorkflowStatusDto request) {
        Workflow workflow = workflowRepository.findById(request.workflowId())
            .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", request.workflowId()));
        IssueStatus issueStatus = issueStatusRepository.findById(request.statusId())
            .orElseThrow(() -> new ResourceNotFoundException("IssueStatus", "id", request.statusId()));
        if (workflowStatusRepository.existsByIdWorkflowIdAndIdStatusId(request.workflowId(), request.statusId())) {
            throw new DuplicateResourceException("WorkflowStatus already exists for workflow " + request.workflowId() + " and status " + request.statusId());
        }
        WorkflowStatusId id = new WorkflowStatusId(request.workflowId(), request.statusId());
        WorkflowStatus saved = workflowStatusRepository.save(
            WorkflowStatus.builder().id(id).workflow(workflow).issueStatus(issueStatus).build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<WorkflowStatusDto> listByWorkflow(Long workflowId) {
        if (!workflowRepository.existsById(workflowId)) {
            throw new ResourceNotFoundException("Workflow", "id", workflowId);
        }
        return workflowStatusRepository.findAllByIdWorkflowId(workflowId).stream().map(this::toDto).toList();
    }

    @Transactional
    public void remove(Long workflowId, Long statusId) {
        WorkflowStatusId id = new WorkflowStatusId(workflowId, statusId);
        if (!workflowStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("WorkflowStatus not found for workflow " + workflowId + " and status " + statusId);
        }
        workflowStatusRepository.deleteById(id);
    }

    private WorkflowStatusDto toDto(WorkflowStatus e) {
        return new WorkflowStatusDto(e.getId().getWorkflowId(), e.getId().getStatusId());
    }
}
