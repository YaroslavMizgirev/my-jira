package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateIssueLinkTypeDto;
import ru.mymsoft.my_jira.dto.IssueLinkTypeDto;
import ru.mymsoft.my_jira.dto.UpdateIssueLinkTypeDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.IssueLinkType;
import ru.mymsoft.my_jira.repository.IssueLinkTypeRepository;

@Service
@RequiredArgsConstructor
public class IssueLinkTypeService {

    private final IssueLinkTypeRepository issueLinkTypeRepository;

    @Transactional
    public IssueLinkTypeDto create(CreateIssueLinkTypeDto request) {
        if (issueLinkTypeRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("IssueLinkType", "name", request.name());
        }
        if (issueLinkTypeRepository.existsByInwardName(request.inwardName())) {
            throw new DuplicateResourceException("IssueLinkType", "inwardName", request.inwardName());
        }
        IssueLinkType saved = issueLinkTypeRepository.save(
            IssueLinkType.builder()
                .name(request.name())
                .inwardName(request.inwardName())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public IssueLinkTypeDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<IssueLinkTypeDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return issueLinkTypeRepository.findByNameContainingIgnoreCase(filter).stream()
            .map(this::toDto)
            .collect(java.util.stream.Collectors.collectingAndThen(
                java.util.stream.Collectors.toList(),
                list -> {
                    int start = (int) pageable.getOffset();
                    int end = Math.min(start + pageable.getPageSize(), list.size());
                    return new org.springframework.data.domain.PageImpl<>(
                        start <= end ? list.subList(start, end) : java.util.Collections.emptyList(),
                        pageable, list.size()
                    );
                }
            ));
    }

    @Transactional
    public IssueLinkTypeDto update(Long id, UpdateIssueLinkTypeDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        IssueLinkType existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && issueLinkTypeRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("IssueLinkType", "name", request.name());
        }
        if (!existing.getInwardName().equals(request.inwardName()) && issueLinkTypeRepository.existsByInwardName(request.inwardName())) {
            throw new DuplicateResourceException("IssueLinkType", "inwardName", request.inwardName());
        }
        existing.setName(request.name());
        existing.setInwardName(request.inwardName());
        return toDto(issueLinkTypeRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!issueLinkTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("IssueLinkType", "id", id);
        }
        issueLinkTypeRepository.deleteById(id);
    }

    private IssueLinkType findOrThrow(Long id) {
        return issueLinkTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("IssueLinkType", "id", id));
    }

    private IssueLinkTypeDto toDto(IssueLinkType e) {
        return new IssueLinkTypeDto(e.getId(), e.getName(), e.getInwardName());
    }
}
