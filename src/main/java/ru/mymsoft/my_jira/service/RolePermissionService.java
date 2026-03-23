package ru.mymsoft.my_jira.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.mymsoft.my_jira.dto.CreateRolePermissionDto;
import ru.mymsoft.my_jira.dto.RolePermissionDto;
import ru.mymsoft.my_jira.exception.DuplicateResourceException;
import ru.mymsoft.my_jira.exception.ResourceNotFoundException;
import ru.mymsoft.my_jira.model.Permission;
import ru.mymsoft.my_jira.model.Role;
import ru.mymsoft.my_jira.model.RolePermission;
import ru.mymsoft.my_jira.model.RolePermission.RolePermissionId;
import ru.mymsoft.my_jira.repository.PermissionRepository;
import ru.mymsoft.my_jira.repository.RolePermissionRepository;
import ru.mymsoft.my_jira.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public RolePermissionDto assign(CreateRolePermissionDto request) {
        Role role = roleRepository.findById(request.roleId())
            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", request.roleId()));
        Permission permission = permissionRepository.findById(request.permissionId())
            .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", request.permissionId()));
        if (rolePermissionRepository.existsByRoleIdAndPermissionId(request.roleId(), request.permissionId())) {
            throw new DuplicateResourceException("RolePermission already exists for role " + request.roleId() + " and permission " + request.permissionId());
        }
        RolePermission saved = rolePermissionRepository.save(
            RolePermission.builder().role(role).permission(permission).build()
        );
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<RolePermissionDto> listByRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role", "id", roleId);
        }
        return rolePermissionRepository.findAllByRoleId(roleId).stream().map(this::toDto).toList();
    }

    @Transactional
    public void revoke(Long roleId, Long permissionId) {
        if (!rolePermissionRepository.existsByRoleIdAndPermissionId(roleId, permissionId)) {
            throw new ResourceNotFoundException("RolePermission not found for role " + roleId + " and permission " + permissionId);
        }
        rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    private RolePermissionDto toDto(RolePermission e) {
        return new RolePermissionDto(e.getRole().getId(), e.getPermission().getId());
    }
}
