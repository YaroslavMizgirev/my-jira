package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotificationStatusTest {

    @Test
    @DisplayName("Should create NotificationStatus with builder pattern")
    void shouldCreateNotificationStatusWithBuilder() {
        // When
        NotificationStatus status = NotificationStatus.builder()
                .id(1L)
                .name("PENDING")
                .isDefault(true)
                .build();

        // Then
        assertNotNull(status);
        assertEquals(1L, status.getId());
        assertEquals("PENDING", status.getName());
        assertTrue(status.getIsDefault());
    }

    @Test
    @DisplayName("Should create NotificationStatus with no-args constructor")
    void shouldCreateNotificationStatusWithNoArgsConstructor() {
        // When
        NotificationStatus status = new NotificationStatus();

        // Then
        assertNotNull(status);
        assertNull(status.getId());
        assertNull(status.getName());
        assertFalse(status.getIsDefault()); // Default value from field initialization
    }

    @Test
    @DisplayName("Should create NotificationStatus with all-args constructor")
    void shouldCreateNotificationStatusWithAllArgsConstructor() {
        // When
        NotificationStatus status = new NotificationStatus(1L, "SENT", false);

        // Then
        assertNotNull(status);
        assertEquals(1L, status.getId());
        assertEquals("SENT", status.getName());
        assertFalse(status.getIsDefault());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        NotificationStatus status = new NotificationStatus();

        // When
        status.setId(5L);
        status.setName("FAILED");
        status.setIsDefault(false);

        // Then
        assertEquals(5L, status.getId());
        assertEquals("FAILED", status.getName());
        assertFalse(status.getIsDefault());
    }

    @Test
    @DisplayName("Should have default value for isDefault field")
    void shouldHaveDefaultValueForIsDefault() {
        // When - using no-args constructor
        NotificationStatus status = new NotificationStatus();

        // Then
        assertFalse(status.getIsDefault());

        // When - using builder without explicit isDefault
        NotificationStatus status2 = NotificationStatus.builder()
                .id(1L)
                .name("PROCESSING")
                .build();

        // Then
        assertFalse(status2.getIsDefault());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on all fields")
    void shouldImplementEqualsAndHashCodeBasedOnAllFields() {
        // Given
        NotificationStatus status1 = NotificationStatus.builder()
                .id(1L)
                .name("PENDING")
                .isDefault(false)
                .build();

        NotificationStatus status2 = NotificationStatus.builder()
                .id(1L)
                .name("PENDING")
                .isDefault(false)
                .build();

        NotificationStatus status3 = NotificationStatus.builder()
                .id(2L) // Different ID
                .name("PENDING")
                .isDefault(false)
                .build();

        NotificationStatus status4 = NotificationStatus.builder()
                .id(1L)
                .name("SENT") // Different name
                .isDefault(false)
                .build();

        NotificationStatus status5 = NotificationStatus.builder()
                .id(1L)
                .name("PENDING")
                .isDefault(true) // Different isDefault
                .build();

        NotificationStatus status6 = new NotificationStatus(); // null fields

        // Then
        // Same values should be equal
        assertEquals(status1, status2);
        assertEquals(status1.hashCode(), status2.hashCode());

        // Different ID should not be equal
        assertNotEquals(status1, status3);

        // Different name should not be equal
        assertNotEquals(status1, status4);

        // Different isDefault should not be equal
        assertNotEquals(status1, status5);

        // Null comparison
        assertNotEquals(status1, null);
        assertNotEquals(status1, "some string");
        assertNotEquals(status1, status6);

        // Reflexivity
        assertEquals(status1, status1);

        // Symmetry
        assertEquals(status1, status2);
        assertEquals(status2, status1);

        // Consistency
        assertEquals(status1.hashCode(), status1.hashCode());
        assertEquals(status1, status1);
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        NotificationStatus status1 = NotificationStatus.builder()
                .id(1L)
                .name("PENDING")
                .isDefault(false)
                .build();

        NotificationStatus status2 = NotificationStatus.builder()
                .id(1L)
                .name("PENDING")
                .isDefault(false)
                .build(); // Equal to status1

        NotificationStatus status3 = NotificationStatus.builder()
                .id(2L)
                .name("SENT")
                .isDefault(true)
                .build(); // Different

        Set<NotificationStatus> set = new HashSet<>();

        // When
        set.add(status1);
        set.add(status2); // Duplicate
        set.add(status3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates");
        assertTrue(set.contains(status1));
        assertTrue(set.contains(status2));
        assertTrue(set.contains(status3));
    }

    @Test
    @DisplayName("Should handle null ID in equals and hashCode")
    void shouldHandleNullIdInEqualsAndHashCode() {
        // Given
        NotificationStatus status1 = new NotificationStatus();
        status1.setName("PENDING");
        status1.setIsDefault(false);

        NotificationStatus status2 = new NotificationStatus();
        status2.setName("PENDING");
        status2.setIsDefault(false);

        NotificationStatus status3 = new NotificationStatus();
        status3.setName("SENT"); // Different name
        status3.setIsDefault(false);

        // Then
        assertEquals(status1, status2);
        assertNotEquals(status1, status3);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should handle null and empty names")
    void shouldHandleNullAndEmptyNames(String name) {
        // Given
        NotificationStatus status = new NotificationStatus();

        // When
        status.setName(name);

        // Then
        assertEquals(name, status.getName());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        NotificationStatus status = NotificationStatus.builder()
                .id(1L)
                .name("DELIVERED")
                .isDefault(true)
                .build();

        // When
        String toStringResult = status.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some field information
        assertTrue(toStringResult.contains("NotificationStatus") ||
                  toStringResult.contains("DELIVERED"));
    }

    @Test
    @DisplayName("Should handle different boolean values for isDefault")
    void shouldHandleDifferentBooleanValuesForIsDefault() {
        // Test with true
        NotificationStatus status1 = NotificationStatus.builder()
                .id(1L)
                .name("STATUS1")
                .isDefault(true)
                .build();

        // Test with false
        NotificationStatus status2 = NotificationStatus.builder()
                .id(2L)
                .name("STATUS2")
                .isDefault(false)
                .build();

        // Test with null (should use default false)
        NotificationStatus status3 = new NotificationStatus();
        status3.setId(3L);
        status3.setName("STATUS3");
        // isDefault not set

        // Then
        assertTrue(status1.getIsDefault());
        assertFalse(status2.getIsDefault());
        assertFalse(status3.getIsDefault());
    }

    @Test
    @DisplayName("Should verify field constraints through getters")
    void shouldVerifyFieldConstraintsThroughGetters() {
        // Given
        NotificationStatus status = NotificationStatus.builder()
                .id(100L)
                .name("COMPLETED")
                .isDefault(false)
                .build();

        // When & Then
        assertEquals(100L, status.getId());
        assertEquals("COMPLETED", status.getName());
        assertFalse(status.getIsDefault());
    }

    @Test
    @DisplayName("Should handle maximum length for name field")
    void shouldHandleMaximumLengthForNameField() {
        // Given - name with maximum allowed length (20 chars)
        String maxLengthName = "ABCDEFGHIJKLMNOPQRST";

        // When
        NotificationStatus status = NotificationStatus.builder()
                .id(1L)
                .name(maxLengthName)
                .isDefault(false)
                .build();

        // Then
        assertEquals(maxLengthName, status.getName());
        assertEquals(20, status.getName().length());
    }

    @Test
    @DisplayName("Should create multiple status instances with different states")
    void shouldCreateMultipleStatusInstancesWithDifferentStates() {
        // Given
        NotificationStatus pending = NotificationStatus.builder()
                .id(1L)
                .name("PENDING")
                .isDefault(true)
                .build();

        NotificationStatus sent = NotificationStatus.builder()
                .id(2L)
                .name("SENT")
                .isDefault(false)
                .build();

        NotificationStatus failed = NotificationStatus.builder()
                .id(3L)
                .name("FAILED")
                .isDefault(false)
                .build();

        // Then
        assertAll(
                () -> assertEquals("PENDING", pending.getName()),
                () -> assertTrue(pending.getIsDefault()),
                () -> assertEquals("SENT", sent.getName()),
                () -> assertFalse(sent.getIsDefault()),
                () -> assertEquals("FAILED", failed.getName()),
                () -> assertFalse(failed.getIsDefault())
        );
    }
}
