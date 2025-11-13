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

class PriorityTest {

    @Test
    @DisplayName("Should create Priority with builder pattern")
    void shouldCreatePriorityWithBuilder() {
        // When
        Priority priority = Priority.builder()
                .id(1L)
                .level(1)
                .name("Highest")
                .iconUrl("/icons/priority-highest.svg")
                .colorHexCode("#FF0000")
                .build();

        // Then
        assertNotNull(priority);
        assertEquals(1L, priority.getId());
        assertEquals(1, priority.getLevel());
        assertEquals("Highest", priority.getName());
        assertEquals("/icons/priority-highest.svg", priority.getIconUrl());
        assertEquals("#FF0000", priority.getColorHexCode());
    }

    @Test
    @DisplayName("Should create Priority with no-args constructor")
    void shouldCreatePriorityWithNoArgsConstructor() {
        // When
        Priority priority = new Priority();

        // Then
        assertNotNull(priority);
        assertNull(priority.getId());
        assertNull(priority.getLevel());
        assertNull(priority.getName());
        assertNull(priority.getIconUrl());
        assertNull(priority.getColorHexCode());
    }

    @Test
    @DisplayName("Should create Priority with all-args constructor")
    void shouldCreatePriorityWithAllArgsConstructor() {
        // When
        Priority priority = new Priority(
                1L,
                2,
                "High",
                "/icons/priority-high.svg",
                "#FF4444"
        );

        // Then
        assertNotNull(priority);
        assertEquals(1L, priority.getId());
        assertEquals(2, priority.getLevel());
        assertEquals("High", priority.getName());
        assertEquals("/icons/priority-high.svg", priority.getIconUrl());
        assertEquals("#FF4444", priority.getColorHexCode());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        Priority priority = new Priority();

        // When
        priority.setId(5L);
        priority.setLevel(3);
        priority.setName("Medium");
        priority.setIconUrl("/icons/priority-medium.png");
        priority.setColorHexCode("#FFAA00");

        // Then
        assertEquals(5L, priority.getId());
        assertEquals(3, priority.getLevel());
        assertEquals("Medium", priority.getName());
        assertEquals("/icons/priority-medium.png", priority.getIconUrl());
        assertEquals("#FFAA00", priority.getColorHexCode());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id, name and level")
    void shouldImplementEqualsAndHashCodeBasedOnIdNameAndLevel() {
        // Given
        Priority priority1 = Priority.builder()
                .id(1L)
                .level(1)
                .name("Highest")
                .iconUrl("/icons/1.svg")
                .colorHexCode("#FF0000")
                .build();

        Priority priority2 = Priority.builder()
                .id(1L)
                .level(1)
                .name("Highest")
                .iconUrl("/icons/different.svg") // Different icon
                .colorHexCode("#00FF00") // Different color
                .build();

        Priority priority3 = Priority.builder()
                .id(2L) // Different ID
                .level(1)
                .name("Highest")
                .iconUrl("/icons/1.svg")
                .colorHexCode("#FF0000")
                .build();

        Priority priority4 = Priority.builder()
                .id(1L)
                .level(2) // Different level
                .name("Highest")
                .iconUrl("/icons/1.svg")
                .colorHexCode("#FF0000")
                .build();

        Priority priority5 = Priority.builder()
                .id(1L)
                .level(1)
                .name("High") // Different name
                .iconUrl("/icons/1.svg")
                .colorHexCode("#FF0000")
                .build();

        Priority priority6 = new Priority(); // null fields

        // Then
        // Same ID, name and level should be equal regardless of other fields
        assertEquals(priority1, priority2);
        assertEquals(priority1.hashCode(), priority2.hashCode());

        // Different ID should not be equal
        assertNotEquals(priority1, priority3);

        // Different level should not be equal
        assertNotEquals(priority1, priority4);

        // Different name should not be equal
        assertNotEquals(priority1, priority5);

        // Null comparison
        assertNotEquals(priority1, null);
        assertNotEquals(priority1, "some string");
        assertNotEquals(priority1, priority6);

        // Reflexivity
        assertEquals(priority1, priority1);

        // Symmetry
        assertEquals(priority1, priority2);
        assertEquals(priority2, priority1);

        // Consistency
        assertEquals(priority1.hashCode(), priority1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        Priority priority1 = Priority.builder()
                .id(1L)
                .level(1)
                .name("Critical")
                .iconUrl("/icons/critical.svg")
                .colorHexCode("#FF0000")
                .build();

        Priority priority2 = Priority.builder()
                .id(1L)
                .level(1)
                .name("Critical")
                .iconUrl("/icons/different.svg") // Different icon
                .colorHexCode("#0000FF") // Different color
                .build(); // Equal to priority1 (same id, name and level)

        Priority priority3 = Priority.builder()
                .id(2L)
                .level(2)
                .name("High")
                .iconUrl("/icons/high.svg")
                .colorHexCode("#FF4444")
                .build(); // Different

        Set<Priority> set = new HashSet<>();

        // When
        set.add(priority1);
        set.add(priority2); // Duplicate (same id, name and level)
        set.add(priority3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by id, name and level");
        assertTrue(set.contains(priority1));
        assertTrue(set.contains(priority2));
        assertTrue(set.contains(priority3));
    }

    @Test
    @DisplayName("Should handle null fields in equals and hashCode")
    void shouldHandleNullFieldsInEqualsAndHashCode() {
        // Given
        Priority priority1 = new Priority();
        priority1.setLevel(1);

        Priority priority2 = new Priority();
        priority2.setLevel(1);

        Priority priority3 = new Priority();
        priority3.setName("Test"); // Only name set

        // Then
        assertEquals(priority1, priority2);
        assertNotEquals(priority1, priority3);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should handle null and empty names")
    void shouldHandleNullAndEmptyNames(String name) {
        // Given
        Priority priority = new Priority();

        // When
        priority.setName(name);

        // Then
        assertEquals(name, priority.getName());
    }

    @Test
    @DisplayName("Should handle valid color hex codes")
    void shouldHandleValidColorHexCodes() {
        // Given
        String[] validHexCodes = {
            "#FF0000", "#00FF00", "#0000FF", "#FFFFFF", "#000000",
            "#123456", "#ABCDEF", "#abcdef", "#ffaacc"
        };

        // When & Then
        for (int i = 0; i < validHexCodes.length; i++) {
            Priority priority = Priority.builder()
                    .id((long) (i + 1))
                    .level(i + 1)
                    .name("Priority " + (i + 1))
                    .colorHexCode(validHexCodes[i])
                    .build();

            assertEquals(validHexCodes[i], priority.getColorHexCode());
        }
    }

    @Test
    @DisplayName("Should handle null optional fields")
    void shouldHandleNullOptionalFields() {
        // When
        Priority priority = Priority.builder()
                .id(1L)
                .level(1)
                .name("Required Priority")
                .iconUrl(null) // Optional field
                .colorHexCode(null) // Optional field
                .build();

        // Then
        assertNotNull(priority);
        assertEquals("Required Priority", priority.getName());
        assertNull(priority.getIconUrl());
        assertNull(priority.getColorHexCode());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        Priority priority = Priority.builder()
                .id(1L)
                .level(1)
                .name("Urgent")
                .iconUrl("/icons/urgent.png")
                .colorHexCode("#FF2222")
                .build();

        // When
        String toStringResult = priority.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("Priority") ||
                  toStringResult.contains("Urgent") ||
                  toStringResult.contains("level=1"));
    }

    @Test
    @DisplayName("Should create priority with only required fields")
    void shouldCreatePriorityWithOnlyRequiredFields() {
        // When
        Priority priority = Priority.builder()
                .level(5)
                .name("Lowest")
                .build();

        // Then
        assertNotNull(priority);
        assertEquals(5, priority.getLevel());
        assertEquals("Lowest", priority.getName());
        assertNull(priority.getId());
        assertNull(priority.getIconUrl());
        assertNull(priority.getColorHexCode());
    }

    @ParameterizedTest
    @CsvSource({
        "1, 'Highest', '/icons/highest.svg', '#FF0000'",
        "2, 'High', '/icons/high.svg', '#FF4444'", 
        "3, 'Medium', '/icons/medium.svg', '#FFAA00'",
        "4, 'Low', '/icons/low.svg', '#00FF00'",
        "5, 'Lowest', '/icons/lowest.svg', '#0000FF'"
    })
    @DisplayName("Should create different priority levels with various properties")
    void shouldCreateDifferentPriorityLevels(int level, String name, String iconUrl, String colorHexCode) {
        // When
        Priority priority = Priority.builder()
                .level(level)
                .name(name)
                .iconUrl(iconUrl)
                .colorHexCode(colorHexCode)
                .build();

        // Then
        assertEquals(level, priority.getLevel());
        assertEquals(name, priority.getName());
        assertEquals(iconUrl, priority.getIconUrl());
        assertEquals(colorHexCode, priority.getColorHexCode());
    }

    @Test
    @DisplayName("Should handle priority level ordering")
    void shouldHandlePriorityLevelOrdering() {
        // Given
        Priority highest = Priority.builder().level(1).name("Highest").build();
        Priority high = Priority.builder().level(2).name("High").build();
        Priority medium = Priority.builder().level(3).name("Medium").build();
        Priority low = Priority.builder().level(4).name("Low").build();
        Priority lowest = Priority.builder().level(5).name("Lowest").build();

        // Then - Verify level ordering
        assertTrue(highest.getLevel() < high.getLevel());
        assertTrue(high.getLevel() < medium.getLevel());
        assertTrue(medium.getLevel() < low.getLevel());
        assertTrue(low.getLevel() < lowest.getLevel());
    }

    @Test
    @DisplayName("Should handle icon URL formats")
    void shouldHandleIconUrlFormats() {
        // Given
        String[] iconUrls = {
            "/icons/priority.svg",
            "https://example.com/icons/priority.png",
            "assets/icons/priority.jpg",
            "data:image/svg+xml;base64,PHN2Zy...",
            "/custom/path/to/icon.svg"
        };

        // When & Then
        for (int i = 0; i < iconUrls.length; i++) {
            Priority priority = Priority.builder()
                    .level(i + 1)
                    .name("Priority " + (i + 1))
                    .iconUrl(iconUrls[i])
                    .build();

            assertEquals(iconUrls[i], priority.getIconUrl());
        }
    }

    @Test
    @DisplayName("Should verify name length constraints")
    void shouldVerifyNameLengthConstraints() {
        // Given - name with maximum allowed length (50 chars)
        String maxLengthName = "P".repeat(50);

        // When
        Priority priority = Priority.builder()
                .level(1)
                .name(maxLengthName)
                .build();

        // Then
        assertEquals(maxLengthName, priority.getName());
        assertEquals(50, priority.getName().length());
    }

    @Test
    @DisplayName("Should handle color hex code length constraints")
    void shouldHandleColorHexCodeLengthConstraints() {
        // Given - valid hex codes with exact 7 characters
        String[] validHexCodes = {"#FFFFFF", "#000000", "#123ABC", "#DEF456"};

        // When & Then
        for (String hexCode : validHexCodes) {
            Priority priority = Priority.builder()
                    .level(1)
                    .name("Test")
                    .colorHexCode(hexCode)
                    .build();

            assertEquals(hexCode, priority.getColorHexCode());
            assertEquals(7, priority.getColorHexCode().length());
        }
    }

    @Test
    @DisplayName("Should update priority properties")
    void shouldUpdatePriorityProperties() {
        // Given
        Priority priority = Priority.builder()
                .id(1L)
                .level(3)
                .name("Old Name")
                .iconUrl("/old/icon.svg")
                .colorHexCode("#000000")
                .build();

        // When
        priority.setLevel(1);
        priority.setName("New Name");
        priority.setIconUrl("/new/icon.png");
        priority.setColorHexCode("#FFFFFF");

        // Then
        assertEquals(1, priority.getLevel());
        assertEquals("New Name", priority.getName());
        assertEquals("/new/icon.png", priority.getIconUrl());
        assertEquals("#FFFFFF", priority.getColorHexCode());
    }

    @Test
    @DisplayName("Should handle priority with same level but different names")
    void shouldHandlePriorityWithSameLevelButDifferentNames() {
        // Given
        Priority priority1 = Priority.builder()
                .id(1L)
                .level(1)
                .name("Critical")
                .build();

        Priority priority2 = Priority.builder()
                .id(2L)
                .level(1)
                .name("Urgent") // Same level, different name
                .build();

        // Then
        assertNotEquals(priority1, priority2);
        assertEquals(priority1.getLevel(), priority2.getLevel());
        assertNotEquals(priority1.getName(), priority2.getName());
    }

    @Test
    @DisplayName("Should create realistic priority hierarchy")
    void shouldCreateRealisticPriorityHierarchy() {
        // Given - Typical priority levels for issue tracking system
        Priority blocker = Priority.builder()
                .id(1L)
                .level(1)
                .name("Blocker")
                .iconUrl("/icons/blocker.svg")
                .colorHexCode("#FF0000")
                .build();

        Priority critical = Priority.builder()
                .id(2L)
                .level(2)
                .name("Critical")
                .iconUrl("/icons/critical.svg")
                .colorHexCode("#FF4444")
                .build();

        Priority major = Priority.builder()
                .id(3L)
                .level(3)
                .name("Major")
                .iconUrl("/icons/major.svg")
                .colorHexCode("#FF8800")
                .build();

        Priority minor = Priority.builder()
                .id(4L)
                .level(4)
                .name("Minor")
                .iconUrl("/icons/minor.svg")
                .colorHexCode("#00AA00")
                .build();

        Priority trivial = Priority.builder()
                .id(5L)
                .level(5)
                .name("Trivial")
                .iconUrl("/icons/trivial.svg")
                .colorHexCode("#0000FF")
                .build();

        // Then
        assertAll(
                () -> assertEquals("Blocker", blocker.getName()),
                () -> assertEquals(1, blocker.getLevel()),
                () -> assertEquals("Critical", critical.getName()),
                () -> assertEquals(2, critical.getLevel()),
                () -> assertEquals("Major", major.getName()),
                () -> assertEquals(3, major.getLevel()),
                () -> assertEquals("Minor", minor.getName()),
                () -> assertEquals(4, minor.getLevel()),
                () -> assertEquals("Trivial", trivial.getName()),
                () -> assertEquals(5, trivial.getLevel())
        );
    }
}
