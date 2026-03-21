package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateIssueStatusDto;
import ru.mymsoft.my_jira.dto.IssueStatusDto;
import ru.mymsoft.my_jira.dto.UpdateIssueStatusDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.IssueStatus;
import ru.mymsoft.my_jira.repository.IssueStatusRepository;

@Service
@RequiredArgsConstructor
public class IssueStatusService {

    private final IssueStatusRepository issueStatusRepository;

    @Transactional
    public IssueStatusDto createIssueStatus(CreateIssueStatusDto request) {
        if (issueStatusRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("IssueStatus", "name", request.name());
        }
        IssueStatus saved = issueStatusRepository.save(
            IssueStatus.builder()
                .name(request.name())
                .description(request.description())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public IssueStatusDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<IssueStatusDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return issueStatusRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public IssueStatusDto update(Long id, UpdateIssueStatusDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        IssueStatus existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && issueStatusRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("IssueStatus", "name", request.name());
        }
        existing.setName(request.name());
        existing.setDescription(request.description());
        return toDto(issueStatusRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!issueStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("IssueStatus", "id", id);
        }
        issueStatusRepository.deleteById(id);
    }

    private IssueStatus findOrThrow(Long id) {
        return issueStatusRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("IssueStatus", "id", id));
    }

    private IssueStatusDto toDto(IssueStatus e) {
        return new IssueStatusDto(e.getId(), e.getName(), e.getDescription());
    }
}
