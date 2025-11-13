package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("Should create Role with builder pattern")
    void shouldCreateRoleWithBuilder() {
        // When
        Role role = Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("System administrator with full access")
                .isSystemRole(true)
                .build();

        // Then
        assertNotNull(role);
        assertEquals(1L, role.getId());
        assertEquals("ADMIN", role.getName());
        assertEquals("System administrator with full access", role.getDescription());
        assertTrue(role.getIsSystemRole());
    }

    @Test
    @DisplayName("Should create Role with no-args constructor")
    void shouldCreateRoleWithNoArgsConstructor() {
        // When
        Role role = new Role();

        // Then
        assertNotNull(role);
        assertNull(role.getId());
        assertNull(role.getName());
        assertNull(role.getDescription());
        assertFalse(role.getIsSystemRole()); // Default value
    }

    @Test
    @DisplayName("Should create Role with all-args constructor")
    void shouldCreateRoleWithAllArgsConstructor() {
        // When
        Role role = new Role(
                1L,
                "DEVELOPER",
                "Software developer role with project access",
                false
        );

        // Then
        assertNotNull(role);
        assertEquals(1L, role.getId());
        assertEquals("DEVELOPER", role.getName());
        assertEquals("Software developer role with project access", role.getDescription());
        assertFalse(role.getIsSystemRole());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        Role role = new Role();

        // When
        role.setId(5L);
        role.setName("VIEWER");
        role.setDescription("Read-only access role");
        role.setIsSystemRole(false);

        // Then
        assertEquals(5L, role.getId());
        assertEquals("VIEWER", role.getName());
        assertEquals("Read-only access role", role.getDescription());
        assertFalse(role.getIsSystemRole());
    }

    @Test
    @DisplayName("Should have default value for isSystemRole field")
    void shouldHaveDefaultValueForIsSystemRole() {
        // When - using no-args constructor
        Role role = new Role();

        // Then
        assertFalse(role.getIsSystemRole());

        // When - using builder without explicit isSystemRole
        Role role2 = Role.builder()
                .name("TEST_ROLE")
                .description("Test role")
                .build();

        // Then
        assertFalse(role2.getIsSystemRole());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id and name")
    void shouldImplementEqualsAndHashCodeBasedOnIdAndName() {
        // Given
        Role role1 = Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("Description 1")
                .isSystemRole(true)
                .build();

        Role role2 = Role.builder()
                .id(1L)
                .name("ADMIN")
                .description("Different description") // Different description
                .isSystemRole(false) // Different system role flag
                .build();

        Role role3 = Role.builder()
                .id(2L) // Different ID
                .name("ADMIN")
                .description("Description 1")
                .isSystemRole(true)
                .build();

        Role role4 = Role.builder()
                .id(1L)
                .name("USER") // Different name
                .description("Description 1")
                .isSystemRole(true)
                .build();

        Role role5 = new Role(); // null fields

        // Then
        // Same ID and name should be equal regardless of other fields
        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());

        // Different ID should not be equal
        assertNotEquals(role1, role3);

        // Different name should not be equal
        assertNotEquals(role1, role4);

        // Null comparison
        assertNotEquals(role1, null);
        assertNotEquals(role1, "some string");
        assertNotEquals(role1, role5);

        // Reflexivity
        assertEquals(role1, role1);

        // Symmetry
        assertEquals(role1, role2);
        assertEquals(role2, role1);

        // Consistency
        assertEquals(role1.hashCode(), role1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        Role role1 = Role.builder()
                .id(1L)
                .name("ROLE_A")
                .description("Description A")
                .isSystemRole(true)
                .build();

        Role role2 = Role.builder()
                .id(1L)
                .name("ROLE_A")
                .description("Description B") // Different description
                .isSystemRole(false) // Different system role
                .build(); // Equal to role1 (same id and name)

        Role role3 = Role.builder()
                .id(2L)
                .name("ROLE_B")
                .description("Description C")
                .isSystemRole(false)
                .build(); // Different

        Set<Role> set = new HashSet<>();

        // When
        set.add(role1);
        set.add(role2); // Duplicate (same id and name)
        set.add(role3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by id and name");
        assertTrue(set.contains(role1));
        assertTrue(set.contains(role2));
        assertTrue(set.contains(role3));
    }

    @Test
    @DisplayName("Should handle null ID and name in equals and hashCode")
    void shouldHandleNullIdAndNameInEqualsAndHashCode() {
        // Given
        Role role1 = new Role();
        role1.setDescription("Some description");
        role1.setIsSystemRole(true);

        Role role2 = new Role();
        role2.setDescription("Some description");
        role2.setIsSystemRole(true);

        Role role3 = new Role();
        role3.setName("SOME_ROLE"); // Only name set
        role3.setDescription("Some description");
        role3.setIsSystemRole(true);

        // Then
        assertEquals(role1, role2);
        assertNotEquals(role1, role3);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should handle null and empty names")
    void shouldHandleNullAndEmptyNames(String name) {
        // Given
        Role role = new Role();

        // When
        role.setName(name);

        // Then
        assertEquals(name, role.getName());
    }

    @Test
    @DisplayName("Should handle LOB field for description")
    void shouldHandleLOBFieldForDescription() {
        // Given - Large description that would typically be stored as LOB
        String longDescription = "This is a very detailed role description that explains " +
                "all the permissions, access rights, and responsibilities associated with this role. " +
                "It includes information about what actions users with this role can perform, " +
                "what data they can access, and any restrictions that apply. ".repeat(50);

        // When
        Role role = Role.builder()
                .id(1L)
                .name("COMPLEX_ROLE")
                .description(longDescription)
                .isSystemRole(false)
                .build();

        // Then
        assertEquals(longDescription, role.getDescription());
        assertTrue(role.getDescription().length() > 1000);
    }

    @Test
    @DisplayName("Should handle null description")
    void shouldHandleNullDescription() {
        // When
        Role role = Role.builder()
                .id(1L)
                .name("MINIMAL_ROLE")
                .description(null) // Description can be null
                .isSystemRole(false)
                .build();

        // Then
        assertNull(role.getDescription());
        assertEquals("MINIMAL_ROLE", role.getName());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        Role role = Role.builder()
                .id(1L)
                .name("SUPER_ADMIN")
                .description("Highest level administrator")
                .isSystemRole(true)
                .build();

        // When
        String toStringResult = role.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("Role") ||
                  toStringResult.contains("SUPER_ADMIN") ||
                  toStringResult.contains("isSystemRole=true"));
    }

    @Test
    @DisplayName("Should create role with only required fields")
    void shouldCreateRoleWithOnlyRequiredFields() {
        // When
        Role role = Role.builder()
                .name("BASIC_ROLE")
                .build();

        // Then
        assertNotNull(role);
        assertEquals("BASIC_ROLE", role.getName());
        assertNull(role.getId());
        assertNull(role.getDescription());
        assertFalse(role.getIsSystemRole()); // Default value
    }

    @ParameterizedTest
    @CsvSource({
        "ADMIN, 'Full system administrator', true",
        "DEVELOPER, 'Software development team member', false", 
        "TESTER, 'Quality assurance team member', false",
        "VIEWER, 'Read-only access role', false",
        "MANAGER, 'Project management role', false",
        "GUEST, 'Limited guest access', true"
    })
    @DisplayName("Should create different role types with various properties")
    void shouldCreateDifferentRoleTypes(String name, String description, boolean isSystemRole) {
        // When
        Role role = Role.builder()
                .name(name)
                .description(description)
                .isSystemRole(isSystemRole)
                .build();

        // Then
        assertEquals(name, role.getName());
        assertEquals(description, role.getDescription());
        assertEquals(isSystemRole, role.getIsSystemRole());
    }

    @Test
    @DisplayName("Should handle system roles vs custom roles")
    void shouldHandleSystemRolesVsCustomRoles() {
        // Given
        Role systemRole = Role.builder()
                .id(1L)
                .name("SYSTEM_ADMIN")
                .description("Built-in system administrator")
                .isSystemRole(true)
                .build();

        Role customRole = Role.builder()
                .id(2L)
                .name("CUSTOM_DEVELOPER")
                .description("Custom developer role for specific project")
                .isSystemRole(false)
                .build();

        // Then
        assertTrue(systemRole.getIsSystemRole());
        assertFalse(customRole.getIsSystemRole());
        assertEquals("SYSTEM_ADMIN", systemRole.getName());
        assertEquals("CUSTOM_DEVELOPER", customRole.getName());
    }

    @Test
    @DisplayName("Should verify name length constraints")
    void shouldVerifyNameLengthConstraints() {
        // Given - name with maximum allowed length (100 chars)
        String maxLengthName = "R".repeat(100);

        // When
        Role role = Role.builder()
                .id(1L)
                .name(maxLengthName)
                .description("Role with max length name")
                .isSystemRole(false)
                .build();

        // Then
        assertEquals(maxLengthName, role.getName());
        assertEquals(100, role.getName().length());
    }

    @Test
    @DisplayName("Should handle role with special characters in description")
    void shouldHandleRoleWithSpecialCharactersInDescription() {
        // Given
        String descriptionWithSpecialChars = "Role permissions include: \n" +
                "- Create & edit issues (CRUD operations) \n" +
                - Delete issues <with restrictions> \n" +
                - Manage project settings (use carefully!) \n" +
                "Special chars: @#$%^&*()[]{}";

        // When
        Role role = Role.builder()
                .id(1L)
                .name("POWER_USER")
                .description(descriptionWithSpecialChars)
                .isSystemRole(false)
                .build();

        // Then
        assertNotNull(role);
        assertEquals("POWER_USER", role.getName());
        assertTrue(role.getDescription().contains("CRUD"));
        assertTrue(role.getDescription().contains("@#$%"));
        assertTrue(role.getDescription().contains("\n"));
        assertTrue(role.getDescription().contains("<with restrictions>"));
    }

    @Test
    @DisplayName("Should handle multiple role instances with different states")
    void shouldHandleMultipleRoleInstancesWithDifferentStates() {
        // Given
        Role adminRole = Role.builder()
                .id(1L)
                .name("ADMINISTRATOR")
                .description("Full system access")
                .isSystemRole(true)
                .build();

        Role devRole = Role.builder()
                .id(2L)
                .name("DEVELOPER")
                .description("Development team access")
                .isSystemRole(false)
                .build();

        Role viewerRole = Role.builder()
                .id(3L)
                .name("VIEWER")
                .description("Read-only access")
                .isSystemRole(false)
                .build();

        // Then
        assertAll(
                () -> assertEquals("ADMINISTRATOR", adminRole.getName()),
                () -> assertEquals("Full system access", adminRole.getDescription()),
                () -> assertTrue(adminRole.getIsSystemRole()),
                () -> assertEquals("DEVELOPER", devRole.getName()),
                () -> assertEquals("Development team access", devRole.getDescription()),
                () -> assertFalse(devRole.getIsSystemRole()),
                () -> assertEquals("VIEWER", viewerRole.getName()),
                () -> assertEquals("Read-only access", viewerRole.getDescription()),
                () -> assertFalse(viewerRole.getIsSystemRole())
        );
    }

    @Test
    @DisplayName("Should handle role without description")
    void shouldHandleRoleWithoutDescription() {
        // When
        Role role = Role.builder()
                .id(1L)
                .name("NO_DESC_ROLE")
                // No description set
                .isSystemRole(true)
                .build();

        // Then
        assertNotNull(role);
        assertEquals("NO_DESC_ROLE", role.getName());
        assertNull(role.getDescription());
        assertTrue(role.getIsSystemRole());
    }

    @Test
    @DisplayName("Should update role properties")
    void shouldUpdateRoleProperties() {
        // Given
        Role role = Role.builder()
                .id(1L)
                .name("OLD_NAME")
                .description("Old description")
                .isSystemRole(false)
                .build();

        // When
        role.setName("NEW_NAME");
        role.setDescription("New updated description");
        role.setIsSystemRole(true);

        // Then
        assertEquals("NEW_NAME", role.getName());
        assertEquals("New updated description", role.getDescription());
        assertTrue(role.getIsSystemRole());
    }

    @Test
    @DisplayName("Should toggle system role flag")
    void shouldToggleSystemRoleFlag() {
        // Given
        Role role = Role.builder()
                .id(1L)
                .name("TOGGLE_ROLE")
                .description("Role for testing flag toggling")
                .isSystemRole(false)
                .build();

        // When - toggle to true
        role.setIsSystemRole(true);

        // Then
        assertTrue(role.getIsSystemRole());

        // When - toggle back to false
        role.setIsSystemRole(false);

        // Then
        assertFalse(role.getIsSystemRole());
    }

    @Test
    @DisplayName("Should create realistic system roles")
    void shouldCreateRealisticSystemRoles() {
        // Given - Typical roles in a JIRA-like system
        Role administrator = Role.builder()
                .id(1L)
                .name("Administrator")
                .description("Full system administrator with access to all features and settings")
                .isSystemRole(true)
                .build();

        Role developer = Role.builder()
                .id(2L)
                .name("Developer")
                .description("Can create, edit, and resolve issues in assigned projects")
                .isSystemRole(false)
                .build();

        Role reporter = Role.builder()
                .id(3L)
                .name("Reporter")
                .description("Can create issues and view project content")
                .isSystemRole(false)
                .build();

        Role viewer = Role.builder()
                .id(4L)
                .name("Viewer")
                .description("Read-only access to project content")
                .isSystemRole(false)
                .build();

        // Then
        assertAll(
                () -> assertEquals("Administrator", administrator.getName()),
                () -> assertTrue(administrator.getIsSystemRole()),
                () -> assertEquals("Developer", developer.getName()),
                () -> assertFalse(developer.getIsSystemRole()),
                () -> assertEquals("Reporter", reporter.getName()),
                () -> assertFalse(reporter.getIsSystemRole()),
                () -> assertEquals("Viewer", viewer.getName()),
                () -> assertFalse(viewer.getIsSystemRole())
        );
    }

    @Test
    @DisplayName("Should handle role name case sensitivity")
    void shouldHandleRoleNameCaseSensitivity() {
        // Given
        Role role1 = Role.builder()
                .id(1L)
                .name("admin")
                .description("Lowercase admin")
                .isSystemRole(true)
                .build();

        Role role2 = Role.builder()
                .id(2L)
                .name("ADMIN")
                .description("Uppercase admin")
                .isSystemRole(true)
                .build();

        // Then - names are case-sensitive in equals/hashCode
        assertNotEquals(role1, role2);
        assertNotEquals(role1.getName(), role2.getName());
    }
}
