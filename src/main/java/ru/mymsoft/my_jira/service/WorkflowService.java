package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateWorkflowDto;
import ru.mymsoft.my_jira.dto.UpdateWorkflowDto;
import ru.mymsoft.my_jira.dto.WorkflowDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Workflow;
import ru.mymsoft.my_jira.repository.WorkflowRepository;

@Service
@RequiredArgsConstructor
public class WorkflowService {

    private final WorkflowRepository workflowRepository;

    @Transactional
    public WorkflowDto create(CreateWorkflowDto request) {
        if (workflowRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Workflow", "name", request.name());
        }
        Workflow saved = workflowRepository.save(
            Workflow.builder()
                .name(request.name())
                .isDefault(request.isDefault())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public WorkflowDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<WorkflowDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return workflowRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public WorkflowDto update(Long id, UpdateWorkflowDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        Workflow existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && workflowRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Workflow", "name", request.name());
        }
        existing.setName(request.name());
        existing.setIsDefault(request.isDefault());
        return toDto(workflowRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!workflowRepository.existsById(id)) {
            throw new ResourceNotFoundException("Workflow", "id", id);
        }
        workflowRepository.deleteById(id);
    }

    private Workflow findOrThrow(Long id) {
        return workflowRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Workflow", "id", id));
    }

    private WorkflowDto toDto(Workflow e) {
        return new WorkflowDto(e.getId(), e.getName(), e.getIsDefault());
    }
}
