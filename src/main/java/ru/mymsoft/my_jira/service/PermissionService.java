package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreatePermissionDto;
import ru.mymsoft.my_jira.dto.PermissionDto;
import ru.mymsoft.my_jira.dto.UpdatePermissionDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Permission;
import ru.mymsoft.my_jira.repository.PermissionRepository;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Transactional
    public PermissionDto create(CreatePermissionDto request) {
        if (permissionRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Permission", "name", request.name());
        }
        Permission saved = permissionRepository.save(
            Permission.builder()
                .name(request.name())
                .description(request.description())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public PermissionDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<PermissionDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return permissionRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public PermissionDto update(Long id, UpdatePermissionDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        Permission existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && permissionRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Permission", "name", request.name());
        }
        existing.setName(request.name());
        existing.setDescription(request.description());
        return toDto(permissionRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission", "id", id);
        }
        permissionRepository.deleteById(id);
    }

    private Permission findOrThrow(Long id) {
        return permissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
    }

    private PermissionDto toDto(Permission e) {
        return new PermissionDto(e.getId(), e.getName(), e.getDescription());
    }
}
