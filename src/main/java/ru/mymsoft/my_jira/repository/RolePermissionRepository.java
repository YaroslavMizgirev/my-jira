package ru.mymsoft.my_jira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mymsoft.my_jira.model.RolePermission;
import ru.mymsoft.my_jira.model.RolePermission.RolePermissionId;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
    List<RolePermission> findAllByRoleId(Long roleId);
    List<RolePermission> findAllByPermissionId(Long permissionId);
    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);
    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
