package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RolePermissionTest {

  private Role role;
  private Permission permission;

  @BeforeEach
  void setUp() {
    role = mock(Role.class);
    when(role.getId()).thenReturn(1L);

    permission = mock(Permission.class);
    when(permission.getId()).thenReturn(2L);
  }

  @Test
  @DisplayName("Should create RolePermission with builder pattern")
  void shouldCreateRolePermissionWithBuilder() {
    // When
    RolePermission rolePermission = RolePermission.builder()
        .role(role)
        .permission(permission)
        .build();

    // Then
    assertNotNull(rolePermission);
    assertEquals(role, rolePermission.getRole());
    assertEquals(permission, rolePermission.getPermission());
  }

  @Test
  @DisplayName("Should create RolePermission with no-args constructor")
  void shouldCreateRolePermissionWithNoArgsConstructor() {
    // When
    RolePermission rolePermission = new RolePermission();

    // Then
    assertNotNull(rolePermission);
    assertNull(rolePermission.getRole());
    assertNull(rolePermission.getPermission());
  }

  @Test
  @DisplayName("Should create RolePermission with all-args constructor")
  void shouldCreateRolePermissionWithAllArgsConstructor() {
    // When
    RolePermission rolePermission = new RolePermission(
        role, permission);

    // Then
    assertNotNull(rolePermission);
    assertEquals(role, rolePermission.getRole());
    assertEquals(permission, rolePermission.getPermission());
  }

  @Test
  @DisplayName("Should set and get all properties correctly")
  void shouldSetAndGetAllProperties() {
    // Given
    RolePermission rolePermission = new RolePermission();
    Role newRole = mock(Role.class);
    Permission newPermission = mock(Permission.class);

    // When
    rolePermission.setRole(newRole);
    rolePermission.setPermission(newPermission);

    // Then
    assertEquals(newRole, rolePermission.getRole());
    assertEquals(newPermission, rolePermission.getPermission());
  }

  @Test
  @DisplayName("Should use composite key for entity identity")
  void shouldUseCompositeKeyForEntityIdentity() {
    // Given
    RolePermission rolePermission1 = RolePermission.builder()
        .role(role)
        .permission(permission)
        .build();

    RolePermission rolePermission2 = RolePermission.builder()
        .role(role)
        .permission(permission)
        .build();

    // Then - Same composite key should represent same entity
    assertEquals(rolePermission1.getRole(), rolePermission2.getRole());
    assertEquals(rolePermission1.getPermission(), rolePermission2.getPermission());
  }

  @Test
  @DisplayName("RolePermissionId should implement equals and hashCode correctly")
  void rolePermissionIdShouldImplementEqualsAndHashCode() {
    // Given
    RolePermission.RolePermissionId id1 = new RolePermission.RolePermissionId(1L, 2L);
    RolePermission.RolePermissionId id2 = new RolePermission.RolePermissionId(1L, 2L);
    RolePermission.RolePermissionId id3 = new RolePermission.RolePermissionId(1L, 3L); // Different permission
    RolePermission.RolePermissionId id4 = new RolePermission.RolePermissionId(3L, 2L); // Different role

    // Then
    // Test equals
    assertEquals(id1, id2, "Same role and permission IDs should be equal");
    assertNotEquals(id1, id3, "Different permission IDs should not be equal");
    assertNotEquals(id1, id4, "Different role IDs should not be equal");
    assertNotEquals(id1, null, "Should not equal null");
    assertNotEquals(id1, "some string", "Should not equal different type");

    // Test hashCode
    assertEquals(id1.hashCode(), id2.hashCode(), "Equal objects should have same hash code");
    assertNotEquals(id1.hashCode(), id3.hashCode(), "Different objects should have different hash codes");

    // Test consistency
    assertEquals(id1.hashCode(), id1.hashCode(), "Hash code should be consistent");
  }

  @Test
  @DisplayName("RolePermissionId should work correctly in collections")
  void rolePermissionIdShouldWorkCorrectlyInCollections() {
    // Given
    RolePermission.RolePermissionId id1 = new RolePermission.RolePermissionId(1L, 2L);
    RolePermission.RolePermissionId id2 = new RolePermission.RolePermissionId(1L, 2L);
    RolePermission.RolePermissionId id3 = new RolePermission.RolePermissionId(1L, 3L);

    Set<RolePermission.RolePermissionId> set = new HashSet<>();

    // When
    set.add(id1);
    set.add(id2); // duplicate
    set.add(id3);

    // Then
    assertEquals(2, set.size(), "Set should not contain duplicates");
    assertTrue(set.contains(id1));
    assertTrue(set.contains(id2));
    assertTrue(set.contains(id3));
  }

  @Test
  @DisplayName("RolePermissionId should have working getters and setters")
  void rolePermissionIdShouldHaveWorkingGettersAndSetters() {
    // Given
    RolePermission.RolePermissionId id = new RolePermission.RolePermissionId();

    // When
    id.setRole(10L);
    id.setPermission(20L);

    // Then
    assertEquals(10L, id.getRole());
    assertEquals(20L, id.getPermission());
  }

  @Test
  @DisplayName("RolePermissionId should implement Serializable")
  void rolePermissionIdShouldImplementSerializable() {
    // Given
    RolePermission.RolePermissionId id = new RolePermission.RolePermissionId(1L, 2L);

    // Then
    assertTrue(id instanceof java.io.Serializable, "RolePermissionId should implement Serializable");
  }

  @Test
  @DisplayName("Should create RolePermissionId with all-args constructor")
  void shouldCreateRolePermissionIdWithAllArgsConstructor() {
    // When
    RolePermission.RolePermissionId id = new RolePermission.RolePermissionId(5L, 10L);

    // Then
    assertEquals(5L, id.getRole());
    assertEquals(10L, id.getPermission());
  }

  @Test
  @DisplayName("Should handle null values in composite key")
  void shouldHandleNullValuesInCompositeKey() {
    // Given
    RolePermission.RolePermissionId id1 = new RolePermission.RolePermissionId(null, 2L);
    RolePermission.RolePermissionId id2 = new RolePermission.RolePermissionId(null, 2L);
    RolePermission.RolePermissionId id3 = new RolePermission.RolePermissionId(1L, null);

    // Then
    assertEquals(id1, id2, "Both null role IDs should be considered equal");
    assertNotEquals(id1, id3, "Different null patterns should not be equal");
  }

  @Test
  @DisplayName("Should test toString method for RolePermission")
  void shouldTestToStringMethodForRolePermission() {
    // Given
    RolePermission rolePermission = RolePermission.builder()
        .role(role)
        .permission(permission)
        .build();

    // When
    String toStringResult = rolePermission.toString();

    // Then
    assertNotNull(toStringResult);
    assertFalse(toStringResult.isEmpty());
  }

  @Test
  @DisplayName("Should test toString method for RolePermissionId")
  void shouldTestToStringMethodForRolePermissionId() {
    // Given
    RolePermission.RolePermissionId id = new RolePermission.RolePermissionId(1L, 2L);

    // When
    String toStringResult = id.toString();

    // Then
    assertNotNull(toStringResult);
    assertFalse(toStringResult.isEmpty());
  }

  @Test
  @DisplayName("Should create multiple permissions for same role")
  void shouldCreateMultiplePermissionsForSameRole() {
    // Given
    Permission createPermission = mock(Permission.class);
    when(createPermission.getId()).thenReturn(1L);

    Permission editPermission = mock(Permission.class);
    when(editPermission.getId()).thenReturn(2L);

    Permission deletePermission = mock(Permission.class);
    when(deletePermission.getId()).thenReturn(3L);

    // When
    RolePermission createRolePermission = RolePermission.builder()
        .role(role)
        .permission(createPermission)
        .build();

    RolePermission editRolePermission = RolePermission.builder()
        .role(role)
        .permission(editPermission)
        .build();

    RolePermission deleteRolePermission = RolePermission.builder()
        .role(role)
        .permission(deletePermission)
        .build();

    // Then
    assertEquals(role, createRolePermission.getRole());
    assertEquals(role, editRolePermission.getRole());
    assertEquals(role, deleteRolePermission.getRole());
    assertEquals(createPermission, createRolePermission.getPermission());
    assertEquals(editPermission, editRolePermission.getPermission());
    assertEquals(deletePermission, deleteRolePermission.getPermission());
  }

  @Test
  @DisplayName("Should create same permission for different roles")
  void shouldCreateSamePermissionForDifferentRoles() {
    // Given
    Role adminRole = mock(Role.class);
    when(adminRole.getId()).thenReturn(1L);

    Role userRole = mock(Role.class);
    when(userRole.getId()).thenReturn(2L);

    // When
    RolePermission adminPermission = RolePermission.builder()
        .role(adminRole)
        .permission(permission)
        .build();

    RolePermission userPermission = RolePermission.builder()
        .role(userRole)
        .permission(permission)
        .build();

    // Then
    assertEquals(permission, adminPermission.getPermission());
    assertEquals(permission, userPermission.getPermission());
    assertEquals(adminRole, adminPermission.getRole());
    assertEquals(userRole, userPermission.getRole());
  }

  @Test
  @DisplayName("Should handle realistic role-permission assignments")
  void shouldHandleRealisticRolePermissionAssignments() {
    // Given
    Role adminRole = mock(Role.class);
    when(adminRole.getId()).thenReturn(1L);

    Role developerRole = mock(Role.class);
    when(developerRole.getId()).thenReturn(2L);

    Role viewerRole = mock(Role.class);
    when(viewerRole.getId()).thenReturn(3L);

    Permission createIssue = mock(Permission.class);
    when(createIssue.getId()).thenReturn(1L);

    Permission editIssue = mock(Permission.class);
    when(editIssue.getId()).thenReturn(2L);

    Permission deleteIssue = mock(Permission.class);
    when(deleteIssue.getId()).thenReturn(3L);

    Permission viewIssue = mock(Permission.class);
    when(viewIssue.getId()).thenReturn(4L);

    Permission manageProject = mock(Permission.class);
    when(manageProject.getId()).thenReturn(5L);

    // When - Create realistic role-permission mappings
    RolePermission adminCreate = RolePermission.builder()
        .role(adminRole)
        .permission(createIssue)
        .build();

    RolePermission adminEdit = RolePermission.builder()
        .role(adminRole)
        .permission(editIssue)
        .build();

    RolePermission adminDelete = RolePermission.builder()
        .role(adminRole)
        .permission(deleteIssue)
        .build();

    RolePermission adminView = RolePermission.builder()
        .role(adminRole)
        .permission(viewIssue)
        .build();

    RolePermission adminManage = RolePermission.builder()
        .role(adminRole)
        .permission(manageProject)
        .build();

    RolePermission devCreate = RolePermission.builder()
        .role(developerRole)
        .permission(createIssue)
        .build();

    RolePermission devEdit = RolePermission.builder()
        .role(developerRole)
        .permission(editIssue)
        .build();

    RolePermission devView = RolePermission.builder()
        .role(developerRole)
        .permission(viewIssue)
        .build();

    RolePermission viewerView = RolePermission.builder()
        .role(viewerRole)
        .permission(viewIssue)
        .build();

    // Then
    assertAll(
        () -> assertEquals(adminRole, adminCreate.getRole()),
        () -> assertEquals(createIssue, adminCreate.getPermission()),

        () -> assertEquals(adminRole, adminEdit.getRole()),
        () -> assertEquals(editIssue, adminEdit.getPermission()),

        () -> assertEquals(adminRole, adminDelete.getRole()),
        () -> assertEquals(deleteIssue, adminDelete.getPermission()),

        () -> assertEquals(adminRole, adminView.getRole()),
        () -> assertEquals(viewIssue, adminView.getPermission()),

        () -> assertEquals(adminRole, adminManage.getRole()),
        () -> assertEquals(manageProject, adminManage.getPermission()),

        () -> assertEquals(developerRole, devCreate.getRole()),
        () -> assertEquals(createIssue, devCreate.getPermission()),

        () -> assertEquals(developerRole, devEdit.getRole()),
        () -> assertEquals(editIssue, devEdit.getPermission()),

        () -> assertEquals(developerRole, devView.getRole()),
        () -> assertEquals(viewIssue, devView.getPermission()),

        () -> assertEquals(viewerRole, viewerView.getRole()),
        () -> assertEquals(viewIssue, viewerView.getPermission()));
  }

  @Test
  @DisplayName("Should verify composite key uniqueness constraints")
  void shouldVerifyCompositeKeyUniquenessConstraints() {
    // Given
    RolePermission.RolePermissionId id1 = new RolePermission.RolePermissionId(1L, 2L);
    RolePermission.RolePermissionId id2 = new RolePermission.RolePermissionId(1L, 2L); // Same
    RolePermission.RolePermissionId id3 = new RolePermission.RolePermissionId(1L, 3L); // Different permission
    RolePermission.RolePermissionId id4 = new RolePermission.RolePermissionId(2L, 2L); // Different role

    // Then
    assertEquals(id1, id2); // Same combination
    assertNotEquals(id1, id3); // Different permission
    assertNotEquals(id1, id4); // Different role
  }

  @Test
  @DisplayName("Should handle permission inheritance simulation")
  void shouldHandlePermissionInheritanceSimulation() {
    // Given
    Role baseRole = mock(Role.class);
    when(baseRole.getId()).thenReturn(1L);

    Role extendedRole = mock(Role.class);
    when(extendedRole.getId()).thenReturn(2L);

    Permission readPermission = mock(Permission.class);
    when(readPermission.getId()).thenReturn(1L);

    Permission writePermission = mock(Permission.class);
    when(writePermission.getId()).thenReturn(2L);

    Permission executePermission = mock(Permission.class);
    when(executePermission.getId()).thenReturn(3L);

    // When - Base role has basic permissions
    RolePermission baseRead = RolePermission.builder()
        .role(baseRole)
        .permission(readPermission)
        .build();

    RolePermission baseWrite = RolePermission.builder()
        .role(baseRole)
        .permission(writePermission)
        .build();

    // Extended role inherits base permissions plus additional ones
    RolePermission extendedRead = RolePermission.builder()
        .role(extendedRole)
        .permission(readPermission)
        .build();

    RolePermission extendedWrite = RolePermission.builder()
        .role(extendedRole)
        .permission(writePermission)
        .build();

    RolePermission extendedExecute = RolePermission.builder()
        .role(extendedRole)
        .permission(executePermission)
        .build();

    // Then
    assertEquals(baseRole, baseRead.getRole());
    assertEquals(baseRole, baseWrite.getRole());
    assertEquals(extendedRole, extendedRead.getRole());
    assertEquals(extendedRole, extendedWrite.getRole());
    assertEquals(extendedRole, extendedExecute.getRole());

    // Verify same permissions can be assigned to different roles
    assertEquals(readPermission, baseRead.getPermission());
    assertEquals(readPermission, extendedRead.getPermission());
    assertEquals(writePermission, baseWrite.getPermission());
    assertEquals(writePermission, extendedWrite.getPermission());
  }

  @Test
  @DisplayName("Should handle role with no permissions")
  void shouldHandleRoleWithNoPermissions() {
    // Given
    Role roleWithNoPermissions = mock(Role.class);

    // When - Creating RolePermission instance but not assigning any permissions
    RolePermission emptyRolePermission = new RolePermission();
    emptyRolePermission.setRole(roleWithNoPermissions);
    // permission remains null

    // Then
    assertEquals(roleWithNoPermissions, emptyRolePermission.getRole());
    assertNull(emptyRolePermission.getPermission());
  }

  @Test
  @DisplayName("Should handle complex permission hierarchies")
  void shouldHandleComplexPermissionHierarchies() {
    // Given
    Role superAdmin = mock(Role.class);
    when(superAdmin.getId()).thenReturn(1L);

    Role projectAdmin = mock(Role.class);
    when(projectAdmin.getId()).thenReturn(2L);

    Role teamLead = mock(Role.class);
    when(teamLead.getId()).thenReturn(3L);

    // Multiple permission categories
    Permission[] userPermissions = createMockPermissions(1, 5, "USER_");
    Permission[] projectPermissions = createMockPermissions(6, 10, "PROJECT_");
    Permission[] adminPermissions = createMockPermissions(11, 15, "ADMIN_");

    // When - Assign permissions based on role hierarchy
    // Super admin gets all permissions from all categories
    RolePermission superAdminUserPerm = RolePermission.builder()
        .role(superAdmin)
        .permission(userPermissions[0])
        .build();

    RolePermission superAdminUserPerm2 = RolePermission.builder()
        .role(superAdmin)
        .permission(userPermissions[1])
        .build();

    RolePermission superAdminProjectPerm = RolePermission.builder()
        .role(superAdmin)
        .permission(projectPermissions[0])
        .build();

    RolePermission superAdminProjectPerm2 = RolePermission.builder()
        .role(superAdmin)
        .permission(projectPermissions[1])
        .build();

    RolePermission superAdminAdminPerm = RolePermission.builder()
        .role(superAdmin)
        .permission(adminPermissions[0])
        .build();

    RolePermission superAdminAdminPerm2 = RolePermission.builder()
        .role(superAdmin)
        .permission(adminPermissions[1])
        .build();

    // Project admin gets user and project permissions
    RolePermission projectAdminUserPerm = RolePermission.builder()
        .role(projectAdmin)
        .permission(userPermissions[2])
        .build();

    RolePermission projectAdminUserPerm2 = RolePermission.builder()
        .role(projectAdmin)
        .permission(userPermissions[3])
        .build();

    RolePermission projectAdminProjectPerm = RolePermission.builder()
        .role(projectAdmin)
        .permission(projectPermissions[2])
        .build();

    RolePermission projectAdminProjectPerm2 = RolePermission.builder()
        .role(projectAdmin)
        .permission(projectPermissions[3])
        .build();

    // Team lead gets only user permissions
    RolePermission teamLeadUserPerm = RolePermission.builder()
        .role(teamLead)
        .permission(userPermissions[4])
        .build();

    RolePermission teamLeadUserPerm2 = RolePermission.builder()
        .role(teamLead)
        .permission(userPermissions[0]) // Can have same permission as others
        .build();

    // Then - Verify all created RolePermission objects
    assertAll(
        // Super Admin permissions
        () -> assertEquals(superAdmin, superAdminUserPerm.getRole()),
        () -> assertEquals(userPermissions[0], superAdminUserPerm.getPermission()),
        () -> assertEquals(superAdmin, superAdminUserPerm2.getRole()),
        () -> assertEquals(userPermissions[1], superAdminUserPerm2.getPermission()),
        () -> assertEquals(superAdmin, superAdminProjectPerm.getRole()),
        () -> assertEquals(projectPermissions[0], superAdminProjectPerm.getPermission()),
        () -> assertEquals(superAdmin, superAdminProjectPerm2.getRole()),
        () -> assertEquals(projectPermissions[1], superAdminProjectPerm2.getPermission()),
        () -> assertEquals(superAdmin, superAdminAdminPerm.getRole()),
        () -> assertEquals(adminPermissions[0], superAdminAdminPerm.getPermission()),
        () -> assertEquals(superAdmin, superAdminAdminPerm2.getRole()),
        () -> assertEquals(adminPermissions[1], superAdminAdminPerm2.getPermission()),

        // Project Admin permissions
        () -> assertEquals(projectAdmin, projectAdminUserPerm.getRole()),
        () -> assertEquals(userPermissions[2], projectAdminUserPerm.getPermission()),
        () -> assertEquals(projectAdmin, projectAdminUserPerm2.getRole()),
        () -> assertEquals(userPermissions[3], projectAdminUserPerm2.getPermission()),
        () -> assertEquals(projectAdmin, projectAdminProjectPerm.getRole()),
        () -> assertEquals(projectPermissions[2], projectAdminProjectPerm.getPermission()),
        () -> assertEquals(projectAdmin, projectAdminProjectPerm2.getRole()),
        () -> assertEquals(projectPermissions[3], projectAdminProjectPerm2.getPermission()),

        // Team Lead permissions
        () -> assertEquals(teamLead, teamLeadUserPerm.getRole()),
        () -> assertEquals(userPermissions[4], teamLeadUserPerm.getPermission()),
        () -> assertEquals(teamLead, teamLeadUserPerm2.getRole()),
        () -> assertEquals(userPermissions[0], teamLeadUserPerm2.getPermission()));

    // Additional verification for permission hierarchy
    assertAll(
        // Verify super admin has permissions from all categories
        () -> assertTrue(containsPermission(superAdminUserPerm, userPermissions[0])),
        () -> assertTrue(containsPermission(superAdminProjectPerm, projectPermissions[0])),
        () -> assertTrue(containsPermission(superAdminAdminPerm, adminPermissions[0])),

        // Verify project admin has user and project permissions but no admin
        // permissions
        () -> assertTrue(containsPermission(projectAdminUserPerm, userPermissions[2])),
        () -> assertTrue(containsPermission(projectAdminProjectPerm, projectPermissions[2])),

        // Verify team lead has only user permissions
        () -> assertTrue(containsPermission(teamLeadUserPerm, userPermissions[4])),
        () -> assertTrue(containsPermission(teamLeadUserPerm2, userPermissions[0])));
  }

  // Helper method to check if RolePermission contains specific permission
  private boolean containsPermission(RolePermission rolePermission, Permission permission) {
    return rolePermission.getPermission().equals(permission);
  }

  private Permission[] createMockPermissions(int startId, int endId, String prefix) {
    int count = endId - startId + 1;
    Permission[] permissions = new Permission[count];
    for (int i = 0; i < count; i++) {
      Permission perm = mock(Permission.class);
      when(perm.getId()).thenReturn((long) (startId + i));
      when(perm.getName()).thenReturn(prefix + (startId + i));
      permissions[i] = perm;
    }
    return permissions;
  }
}
