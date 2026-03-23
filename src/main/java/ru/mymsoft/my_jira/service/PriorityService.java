package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreatePriorityDto;
import ru.mymsoft.my_jira.dto.PriorityDto;
import ru.mymsoft.my_jira.dto.UpdatePriorityDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Priority;
import ru.mymsoft.my_jira.repository.PriorityRepository;

@Service
@RequiredArgsConstructor
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Transactional
    public PriorityDto create(CreatePriorityDto request) {
        if (priorityRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Priority", "name", request.name());
        }
        if (priorityRepository.existsByLevel(request.level())) {
            throw new DuplicateResourceException("Priority", "level", request.level());
        }
        Priority saved = priorityRepository.save(
            Priority.builder()
                .level(request.level())
                .name(request.name())
                .iconUrl(request.iconUrl())
                .colorHexCode(request.colorHexCode())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public PriorityDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<PriorityDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return priorityRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public PriorityDto update(Long id, UpdatePriorityDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        Priority existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && priorityRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Priority", "name", request.name());
        }
        if (!existing.getLevel().equals(request.level()) && priorityRepository.existsByLevel(request.level())) {
            throw new DuplicateResourceException("Priority", "level", request.level());
        }
        existing.setLevel(request.level());
        existing.setName(request.name());
        existing.setIconUrl(request.iconUrl());
        existing.setColorHexCode(request.colorHexCode());
        return toDto(priorityRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!priorityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Priority", "id", id);
        }
        priorityRepository.deleteById(id);
    }

    private Priority findOrThrow(Long id) {
        return priorityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Priority", "id", id));
    }

    private PriorityDto toDto(Priority e) {
        return new PriorityDto(e.getId(), e.getLevel(), e.getName(), e.getIconUrl(), e.getColorHexCode());
    }
}
