package ru.mymsoft.my_jira.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateRoleDto;
import ru.mymsoft.my_jira.dto.RoleDto;
import ru.mymsoft.my_jira.dto.UpdateRoleDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Role;
import ru.mymsoft.my_jira.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public RoleDto create(CreateRoleDto request) {
        if (roleRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Role", "name", request.name());
        }
        Role saved = roleRepository.save(
            Role.builder()
                .name(request.name())
                .description(request.description())
                .isSystemRole(request.isSystemRole())
                .build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public RoleDto getById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<RoleDto> list(String namePart, Pageable pageable) {
        String filter = namePart == null ? "" : namePart;
        return roleRepository.findAllByNameContainingIgnoreCase(filter, pageable).map(this::toDto);
    }

    @Transactional
    public RoleDto update(Long id, UpdateRoleDto request) {
        if (!id.equals(request.id())) {
            throw new IllegalArgumentException("ID in path and body must match");
        }
        Role existing = findOrThrow(id);
        if (!existing.getName().equals(request.name()) && roleRepository.existsByName(request.name())) {
            throw new DuplicateResourceException("Role", "name", request.name());
        }
        existing.setName(request.name());
        existing.setDescription(request.description());
        existing.setIsSystemRole(request.isSystemRole());
        return toDto(roleRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role", "id", id);
        }
        roleRepository.deleteById(id);
    }

    private Role findOrThrow(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
    }

    private RoleDto toDto(Role e) {
        return new RoleDto(e.getId(), e.getName(), e.getDescription(), e.getIsSystemRole());
    }
}
