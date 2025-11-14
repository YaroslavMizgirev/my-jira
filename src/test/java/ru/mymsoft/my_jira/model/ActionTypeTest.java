package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ActionTypeTest {

    private ActionType actionType;
    private final Long TEST_ID = 1L;
    private final String TEST_NAME = "CREATE_ISSUE";

    @BeforeEach
    void setUp() {
        actionType = new ActionType(TEST_ID, TEST_NAME);
    }

    @Test
    @DisplayName("Should create ActionType with no-args constructor")
    void testNoArgsConstructor() {
        // When
        ActionType emptyActionType = new ActionType();

        // Then
        assertNotNull(emptyActionType);
        assertNull(emptyActionType.getId());
        assertNull(emptyActionType.getName());
    }

    @Test
    @DisplayName("Should create ActionType with all-args constructor")
    void testAllArgsConstructor() {
        // Then
        assertNotNull(actionType);
        assertEquals(TEST_ID, actionType.getId());
        assertEquals(TEST_NAME, actionType.getName());
    }

    @Test
    @DisplayName("Should create ActionType using builder pattern")
    void testBuilderPattern() {
        // When
        ActionType builtActionType = ActionType.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .build();

        // Then
        assertNotNull(builtActionType);
        assertEquals(TEST_ID, builtActionType.getId());
        assertEquals(TEST_NAME, builtActionType.getName());
    }

    @Test
    @DisplayName("Should set and get id")
    void testIdGetterAndSetter() {
        // Given
        ActionType actionType1 = new ActionType();
        Long newId = 2L;

        // When
        actionType1.setId(newId);

        // Then
        assertEquals(newId, actionType1.getId());
    }

    @Test
    @DisplayName("Should set and get name")
    void testNameGetterAndSetter() {
        // Given
        ActionType actionType1 = new ActionType();
        String newName = "UPDATE_ISSUE";

        // When
        actionType1.setName(newName);

        // Then
        assertEquals(newName, actionType1.getName());
    }

    @Test
    @DisplayName("Should throw NullPointerException when setting null name")
    void testSetNullName() {
        // Given
        ActionType actionType1 = new ActionType();

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            actionType1.setName(null);
        });

        assertTrue(exception.getMessage().contains("name"));
    }

    @Test
    @DisplayName("Should throw NullPointerException in constructor with null name")
    void testConstructorWithNullName() {
        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new ActionType(1L, null);
        });

        assertTrue(exception.getMessage().contains("name"));
    }

    @Test
    @DisplayName("Should throw NullPointerException in builder with null name")
    void testBuilderWithNullName() {
        // When & Then
        NullPointerException exception = assertThrowsExactly(NullPointerException.class, () -> {
            ActionType.builder()
                .id(1L)
                .name(null)
                .build();
        });

        assertTrue(exception.getMessage().contains("name"));
    }

    @Test
    @DisplayName("Should implement equals correctly")
    void testEquals() {
        // Given
        ActionType sameActionType = new ActionType(TEST_ID, TEST_NAME);
        ActionType differentId = new ActionType(2L, TEST_NAME);
        ActionType differentName = new ActionType(TEST_ID, "DIFFERENT_NAME");
        ActionType completelyDifferent = new ActionType(2L, "DIFFERENT_NAME");

        // Then
        assertEquals(actionType, sameActionType, "Objects with same values should be equal");
        assertNotEquals(actionType, differentId, "Objects with different IDs should not be equal");
        assertNotEquals(actionType, differentName, "Objects with different names should not be equal");
        assertNotEquals(actionType, completelyDifferent, "Completely different objects should not be equal");
        assertNotEquals(actionType, null, "Object should not be equal to null");
        assertNotEquals(actionType, new Object(), "Object should not be equal to different type");

        // Reflexivity
        assertEquals(actionType, actionType, "Object should be equal to itself");

        // Symmetry
        assertEquals(sameActionType, actionType, "Equals should be symmetric");
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void testHashCode() {
        // Given
        ActionType sameActionType = new ActionType(TEST_ID, TEST_NAME);
        ActionType differentActionType = new ActionType(2L, "DIFFERENT_NAME");

        // Then
        assertEquals(actionType).hasSameHashCodeAs(sameActionType);
        assertNotEquals(actionType.hashCode(), differentActionType.hashCode(),
                "Different objects should have different hashCode");

        // Consistency
        int firstHashCode = actionType.hashCode();
        int secondHashCode = actionType.hashCode();
        assertEquals(firstHashCode, secondHashCode, "HashCode should be consistent");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        // When
        String toString = actionType.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("id=" + TEST_ID));
        assertTrue(toString.contains("name=" + TEST_NAME));
        assertTrue(toString.contains("ActionType"));
    }

    @Test
    @DisplayName("Should handle null ID in equals and hashCode")
    void testNullIdHandling() {
        // Given
        ActionType actionType1 = new ActionType(null, TEST_NAME);
        ActionType actionType2 = new ActionType(null, TEST_NAME);
        ActionType actionType3 = new ActionType(null, "DIFFERENT_NAME");

        // Then
        assertEquals(actionType1, actionType2, "Objects with null IDs and same names should be equal");
        assertNotEquals(actionType1, actionType3, "Objects with null IDs but different names should not be equal");
        assertEquals(actionType1.hashCode(), actionType2.hashCode(),
                "Objects with null IDs and same names should have same hashCode");
    }

    @Test
    @DisplayName("Should work correctly as JPA entity")
    void testEntityBehavior() {
        // Given
        ActionType actionType1 = ActionType.builder()
                .name("COMMENT_ADDED")
                .build();

        // When & Then
        assertNotNull(actionType1);
        assertEquals("COMMENT_ADDED", actionType1.getName());
        assertNull(actionType1.getId()); // ID будет сгенерирован БД

        // Проверяем, что сущность может быть использована в JPA контексте
        actionType1.setId(100L);
        assertEquals(100L, actionType1.getId());
    }

    @Test
    @DisplayName("Should test builder with partial parameters")
    void testPartialBuilder() {
        // When - builder только с именем
        ActionType actionType1 = ActionType.builder()
                .name("STATUS_CHANGED")
                .build();

        // Then
        assertNotNull(actionType1);
        assertEquals("STATUS_CHANGED", actionType1.getName());
        assertNull(actionType1.getId());

        // When - builder только с ID
        ActionType actionType2 = ActionType.builder()
                .id(5L)
                .name("ASSIGNEE_CHANGED")
                .build();

        // Then
        assertNotNull(actionType2);
        assertEquals(5L, actionType2.getId());
        assertEquals("ASSIGNEE_CHANGED", actionType2.getName());
    }

    @Test
    @DisplayName("Should verify @NonNull annotation behavior in different scenarios")
    void testNonNullAnnotationComprehensive() {
        // Test 1: Direct constructor with null
        assertThrows(NullPointerException.class, () -> new ActionType(1L, null));

        // Test 2: Setter with null
        ActionType actionType1 = new ActionType();
        assertThrowsExactly(NullPointerException.class, () -> actionType1.setName(null));

        // Test 3: Builder with null
        assertThrowsExactly(NullPointerException.class, () -> ActionType.builder().name(null).build());

        // Test 4: Valid non-null values should work
        ActionType valid = new ActionType(1L, "VALID_NAME");
        assertEquals("VALID_NAME", valid.getName());

        ActionType validBuilt = ActionType.builder().name("VALID_NAME").build();
        assertEquals("VALID_NAME", validBuilt.getName());
    }

    @Test
    @DisplayName("Should test equals and hashCode with null values")
    void testEqualsAndHashCodeWithNulls() {
        // Case 1: Both IDs null, same names
        ActionType type1 = new ActionType(null, "TEST");
        ActionType type2 = new ActionType(null, "TEST");
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());

        // Case 2: One ID null, other not null
        ActionType type3 = new ActionType(1L, "TEST");
        ActionType type4 = new ActionType(null, "TEST");
        assertNotEquals(type3, type4);

        // Case 3: Both names same, one ID null
        ActionType type5 = new ActionType(null, "SAME");
        ActionType type6 = new ActionType(1L, "SAME");
        assertNotEquals(type5, type6);
    }
}
