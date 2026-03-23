package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import ru.mymsoft.my_jira.dto.CreateIssueTypeDto;
import ru.mymsoft.my_jira.dto.IssueTypeDto;
import ru.mymsoft.my_jira.dto.UpdateIssueTypeDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.IssueType;
import ru.mymsoft.my_jira.repository.IssueTypeRepository;

@Service
@RequiredArgsConstructor
public class IssueTypeService {

    private final IssueTypeRepository issueTypeRepository;

    @Transactional
    public IssueTypeDto create(CreateIssueTypeDto request) {
        if (issueTypeRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("IssueType", "name", request.name());
        }
        IssueType saved = issueTypeRepository.save(
            IssueType.builder()
                .name(request.name())
                .iconUrl(request.iconUrl())
                .description(request.description())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public IssueTypeDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<IssueTypeDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return issueTypeRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public IssueTypeDto update(Long id, UpdateIssueTypeDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        IssueType existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && issueTypeRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("IssueType", "name", request.name());
        }
        existing.setName(request.name());
        existing.setIconUrl(request.iconUrl());
        existing.setDescription(request.description());
        return toDto(issueTypeRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!issueTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("IssueType", "id", id);
        }
        issueTypeRepository.deleteById(id);
    }

    private IssueType findOrThrow(Long id) {
        return issueTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("IssueType", "id", id));
    }

    private IssueTypeDto toDto(IssueType e) {
        return new IssueTypeDto(e.getId(), e.getName(), e.getIconUrl(), e.getDescription());
    }
}
