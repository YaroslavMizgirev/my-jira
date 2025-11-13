package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    @Test
    @DisplayName("Should create Permission with builder pattern")
    void shouldCreatePermissionWithBuilder() {
        // When
        Permission permission = Permission.builder()
                .id(1L)
                .name("CREATE_ISSUE")
                .description("Allows user to create new issues in projects")
                .build();

        // Then
        assertNotNull(permission);
        assertEquals(1L, permission.getId());
        assertEquals("CREATE_ISSUE", permission.getName());
        assertEquals("Allows user to create new issues in projects", permission.getDescription());
    }

    @Test
    @DisplayName("Should create Permission with no-args constructor")
    void shouldCreatePermissionWithNoArgsConstructor() {
        // When
        Permission permission = new Permission();

        // Then
        assertNotNull(permission);
        assertNull(permission.getId());
        assertNull(permission.getName());
        assertNull(permission.getDescription());
    }

    @Test
    @DisplayName("Should create Permission with all-args constructor")
    void shouldCreatePermissionWithAllArgsConstructor() {
        // When
        Permission permission = new Permission(
                1L,
                "EDIT_ISSUE",
                "Allows user to edit existing issues"
        );

        // Then
        assertNotNull(permission);
        assertEquals(1L, permission.getId());
        assertEquals("EDIT_ISSUE", permission.getName());
        assertEquals("Allows user to edit existing issues", permission.getDescription());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        Permission permission = new Permission();

        // When
        permission.setId(5L);
        permission.setName("DELETE_ISSUE");
        permission.setDescription("Allows user to delete issues");

        // Then
        assertEquals(5L, permission.getId());
        assertEquals("DELETE_ISSUE", permission.getName());
        assertEquals("Allows user to delete issues", permission.getDescription());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id and name")
    void shouldImplementEqualsAndHashCodeBasedOnIdAndName() {
        // Given
        Permission permission1 = Permission.builder()
                .id(1L)
                .name("BROWSE_PROJECTS")
                .description("Description 1")
                .build();

        Permission permission2 = Permission.builder()
                .id(1L)
                .name("BROWSE_PROJECTS")
                .description("Different description") // Different description
                .build();

        Permission permission3 = Permission.builder()
                .id(2L) // Different ID
                .name("BROWSE_PROJECTS")
                .description("Description 1")
                .build();

        Permission permission4 = Permission.builder()
                .id(1L)
                .name("CREATE_PROJECTS") // Different name
                .description("Description 1")
                .build();

        Permission permission5 = new Permission(); // null fields

        // Then
        // Same ID and name should be equal regardless of description
        assertEquals(permission1, permission2);
        assertEquals(permission1.hashCode(), permission2.hashCode());

        // Different ID should not be equal
        assertNotEquals(permission1, permission3);

        // Different name should not be equal
        assertNotEquals(permission1, permission4);

        // Null comparison
        assertNotEquals(permission1, null);
        assertNotEquals(permission1, "some string");
        assertNotEquals(permission1, permission5);

        // Reflexivity
        assertEquals(permission1, permission1);

        // Symmetry
        assertEquals(permission1, permission2);
        assertEquals(permission2, permission1);

        // Consistency
        assertEquals(permission1.hashCode(), permission1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        Permission permission1 = Permission.builder()
                .id(1L)
                .name("PERMISSION_A")
                .description("Description A")
                .build();

        Permission permission2 = Permission.builder()
                .id(1L)
                .name("PERMISSION_A")
                .description("Description B") // Different description
                .build(); // Equal to permission1 (same id and name)

        Permission permission3 = Permission.builder()
                .id(2L)
                .name("PERMISSION_B")
                .description("Description C")
                .build(); // Different

        Set<Permission> set = new HashSet<>();

        // When
        set.add(permission1);
        set.add(permission2); // Duplicate (same id and name)
        set.add(permission3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by id and name");
        assertTrue(set.contains(permission1));
        assertTrue(set.contains(permission2));
        assertTrue(set.contains(permission3));
    }

    @Test
    @DisplayName("Should handle null ID and name in equals and hashCode")
    void shouldHandleNullIdAndNameInEqualsAndHashCode() {
        // Given
        Permission permission1 = new Permission();
        permission1.setDescription("Some description");

        Permission permission2 = new Permission();
        permission2.setDescription("Some description");

        Permission permission3 = new Permission();
        permission3.setName("SOME_PERMISSION"); // Only name set
        permission3.setDescription("Some description");

        // Then
        assertEquals(permission1, permission2);
        assertNotEquals(permission1, permission3);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should handle null and empty names")
    void shouldHandleNullAndEmptyNames(String name) {
        // Given
        Permission permission = new Permission();

        // When
        permission.setName(name);

        // Then
        assertEquals(name, permission.getName());
    }

    @Test
    @DisplayName("Should handle LOB field for description")
    void shouldHandleLOBFieldForDescription() {
        // Given - Large description that would typically be stored as LOB
        String longDescription = "This is a very long permission description that explains in detail " +
                "what this permission allows the user to do. It might include examples, limitations, " +
                "and security considerations. ".repeat(50);

        // When
        Permission permission = Permission.builder()
                .id(1L)
                .name("COMPLEX_OPERATION")
                .description(longDescription)
                .build();

        // Then
        assertEquals(longDescription, permission.getDescription());
        assertTrue(permission.getDescription().length() > 1000);
    }

    @Test
    @DisplayName("Should handle null description")
    void shouldHandleNullDescription() {
        // When
        Permission permission = Permission.builder()
                .id(1L)
                .name("MINIMAL_PERMISSION")
                .description(null) // Description can be null
                .build();

        // Then
        assertNull(permission.getDescription());
        assertEquals("MINIMAL_PERMISSION", permission.getName());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        Permission permission = Permission.builder()
                .id(1L)
                .name("ADMIN_ACCESS")
                .description("Full administrative access")
                .build();

        // When
        String toStringResult = permission.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("Permission") ||
                  toStringResult.contains("ADMIN_ACCESS"));
    }

    @Test
    @DisplayName("Should create permission with only required fields")
    void shouldCreatePermissionWithOnlyRequiredFields() {
        // When
        Permission permission = Permission.builder()
                .name("VIEW_REPORTS")
                .build();

        // Then
        assertNotNull(permission);
        assertEquals("VIEW_REPORTS", permission.getName());
        assertNull(permission.getId());
        assertNull(permission.getDescription());
    }

    @Test
    @DisplayName("Should handle different permission naming conventions")
    void shouldHandleDifferentPermissionNamingConventions() {
        // Given
        String[] permissionNames = {
            "BROWSE_PROJECTS",
            "CREATE_ISSUES", 
            "EDIT_ISSUES",
            "DELETE_ISSUES",
            "ADMINISTER_PROJECTS",
            "VIEW_READONLY",
            "MODIFY_SETTINGS"
        };

        // When & Then
        for (int i = 0; i < permissionNames.length; i++) {
            Permission permission = Permission.builder()
                    .id((long) (i + 1))
                    .name(permissionNames[i])
                    .description("Permission for " + permissionNames[i])
                    .build();

            assertEquals(permissionNames[i], permission.getName());
            assertTrue(permission.getDescription().contains(permissionNames[i]));
        }
    }

    @Test
    @DisplayName("Should verify permission name length constraints")
    void shouldVerifyPermissionNameLengthConstraints() {
        // Given - name with maximum allowed length (100 chars)
        String maxLengthName = "PERM_".repeat(19) + "END"; // Exactly 100 characters

        // When
        Permission permission = Permission.builder()
                .id(1L)
                .name(maxLengthName)
                .description("Permission with max length name")
                .build();

        // Then
        assertEquals(maxLengthName, permission.getName());
        assertEquals(100, permission.getName().length());
    }

    @Test
    @DisplayName("Should handle permission with special characters in description")
    void shouldHandlePermissionWithSpecialCharactersInDescription() {
        // Given
        String descriptionWithSpecialChars = "Permission allows: \n" +
                "- Create issues (CRUD) \n" +
                - Edit issues <with templates> \n" +
                - Delete issues (use with caution!) \n" +
                "Special chars: @#$%^&*()";

        // When
        Permission permission = Permission.builder()
                .id(1L)
                .name("SPECIAL_OPERATIONS")
                .description(descriptionWithSpecialChars)
                .build();

        // Then
        assertNotNull(permission);
        assertEquals("SPECIAL_OPERATIONS", permission.getName());
        assertTrue(permission.getDescription().contains("CRUD"));
        assertTrue(permission.getDescription().contains("@#$%"));
        assertTrue(permission.getDescription().contains("\n"));
    }

    @Test
    @DisplayName("Should handle multiple permission instances with different states")
    void shouldHandleMultiplePermissionInstancesWithDifferentStates() {
        // Given
        Permission readPermission = Permission.builder()
                .id(1L)
                .name("READ_ONLY")
                .description("Read-only access")
                .build();

        Permission writePermission = Permission.builder()
                .id(2L)
                .name("READ_WRITE")
                .description("Read and write access")
                .build();

        Permission adminPermission = Permission.builder()
                .id(3L)
                .name("ADMIN")
                .description("Administrative access with all privileges")
                .build();

        // Then
        assertAll(
                () -> assertEquals("READ_ONLY", readPermission.getName()),
                () -> assertEquals("Read-only access", readPermission.getDescription()),
                () -> assertEquals("READ_WRITE", writePermission.getName()),
                () -> assertEquals("Read and write access", writePermission.getDescription()),
                () -> assertEquals("ADMIN", adminPermission.getName()),
                () -> assertEquals("Administrative access with all privileges", adminPermission.getDescription())
        );
    }

    @Test
    @DisplayName("Should handle permission without description")
    void shouldHandlePermissionWithoutDescription() {
        // When
        Permission permission = Permission.builder()
                .id(1L)
                .name("BASIC_ACCESS")
                // No description set
                .build();

        // Then
        assertNotNull(permission);
        assertEquals("BASIC_ACCESS", permission.getName());
        assertNull(permission.getDescription());
    }

    @Test
    @DisplayName("Should update permission properties")
    void shouldUpdatePermissionProperties() {
        // Given
        Permission permission = Permission.builder()
                .id(1L)
                .name("OLD_NAME")
                .description("Old description")
                .build();

        // When
        permission.setName("NEW_NAME");
        permission.setDescription("New updated description");

        // Then
        assertEquals("NEW_NAME", permission.getName());
        assertEquals("New updated description", permission.getDescription());
    }
}
