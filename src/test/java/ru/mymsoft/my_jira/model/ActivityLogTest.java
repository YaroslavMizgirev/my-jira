package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class ActivityLogTest {

    private ActivityLog activityLog;
    private final Long TEST_ID = 1L;
    private Issue testIssue;
    private User testUser;
    private ActionType testActionType;
    private final String TEST_FIELD_NAME = "status";
    private final String TEST_OLD_VALUE = "OPEN";
    private final String TEST_NEW_VALUE = "IN_PROGRESS";
    private final Instant TEST_CREATED_AT = Instant.now();

    @BeforeEach
    void setUp() {
        // Create test entities
        testIssue = Issue.builder().id(1L).key("TEST-1").title("Test Issue").build();
        testUser = User.builder().id(1L).username("testuser").email("test@example.com").build();
        testActionType = ActionType.builder().id(1L).name("STATUS_CHANGE").build();

        activityLog = new ActivityLog(
            TEST_ID, testIssue, testUser, testActionType,
            TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT
        );
    }

    @Test
    @DisplayName("Should create ActivityLog with no-args constructor")
    void testNoArgsConstructor() {
        // When
        ActivityLog emptyActivityLog = new ActivityLog();

        // Then
        assertNotNull(emptyActivityLog);
        assertNull(emptyActivityLog.getId());
        assertNull(emptyActivityLog.getIssue());
        assertNull(emptyActivityLog.getUser());
        assertNull(emptyActivityLog.getActionType());
        assertNull(emptyActivityLog.getFieldName());
        assertNull(emptyActivityLog.getOldValue());
        assertNull(emptyActivityLog.getNewValue());
        assertNull(emptyActivityLog.getCreatedAt());
    }

    @Test
    @DisplayName("Should create ActivityLog with all-args constructor")
    void testAllArgsConstructor() {
        // Then
        assertNotNull(activityLog);
        assertEquals(TEST_ID, activityLog.getId());
        assertEquals(testIssue, activityLog.getIssue());
        assertEquals(testUser, activityLog.getUser());
        assertEquals(testActionType, activityLog.getActionType());
        assertEquals(TEST_FIELD_NAME, activityLog.getFieldName());
        assertEquals(TEST_OLD_VALUE, activityLog.getOldValue());
        assertEquals(TEST_NEW_VALUE, activityLog.getNewValue());
        assertEquals(TEST_CREATED_AT, activityLog.getCreatedAt());
    }

    @Test
    @DisplayName("Should create ActivityLog using builder pattern")
    void testBuilderPattern() {
        // When
        ActivityLog builtActivityLog = ActivityLog.builder()
                .id(TEST_ID)
                .issue(testIssue)
                .user(testUser)
                .actionType(testActionType)
                .fieldName(TEST_FIELD_NAME)
                .oldValue(TEST_OLD_VALUE)
                .newValue(TEST_NEW_VALUE)
                .createdAt(TEST_CREATED_AT)
                .build();

        // Then
        assertNotNull(builtActivityLog);
        assertEquals(TEST_ID, builtActivityLog.getId());
        assertEquals(testIssue, builtActivityLog.getIssue());
        assertEquals(testUser, builtActivityLog.getUser());
        assertEquals(testActionType, builtActivityLog.getActionType());
        assertEquals(TEST_FIELD_NAME, builtActivityLog.getFieldName());
        assertEquals(TEST_OLD_VALUE, builtActivityLog.getOldValue());
        assertEquals(TEST_NEW_VALUE, builtActivityLog.getNewValue());
        assertEquals(TEST_CREATED_AT, builtActivityLog.getCreatedAt());
    }

    @Test
    @DisplayName("Should set and get id")
    void testIdGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        Long newId = 2L;

        // When
        log.setId(newId);

        // Then
        assertEquals(newId, log.getId());
    }

    @Test
    @DisplayName("Should set and get issue")
    void testIssueGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        Issue newIssue = Issue.builder().id(2L).key("TEST-2").title("New Issue").build();

        // When
        log.setIssue(newIssue);

        // Then
        assertEquals(newIssue, log.getIssue());
    }

    @Test
    @DisplayName("Should throw NullPointerException when setting null issue")
    void testSetNullIssue() {
        // Given
        ActivityLog log = new ActivityLog();

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            log.setIssue(null);
        });

        assertTrue(exception.getMessage().contains("issue"));
    }

    @Test
    @DisplayName("Should set and get user")
    void testUserGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        User newUser = User.builder().id(2L).username("newuser").email("new@example.com").build();

        // When
        log.setUser(newUser);

        // Then
        assertEquals(newUser, log.getUser());
    }

    @Test
    @DisplayName("Should allow null user")
    void testSetNullUser() {
        // Given
        ActivityLog log = new ActivityLog();

        // When
        log.setUser(null);

        // Then - не должно быть исключения
        assertNull(log.getUser());
    }

    @Test
    @DisplayName("Should set and get actionType")
    void testActionTypeGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        ActionType newActionType = ActionType.builder().id(2L).name("COMMENT_ADDED").build();

        // When
        log.setActionType(newActionType);

        // Then
        assertEquals(newActionType, log.getActionType());
    }

    @Test
    @DisplayName("Should throw NullPointerException when setting null actionType")
    void testSetNullActionType() {
        // Given
        ActivityLog log = new ActivityLog();

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            log.setActionType(null);
        });

        assertTrue(exception.getMessage().contains("actionType"));
    }

    @Test
    @DisplayName("Should set and get fieldName")
    void testFieldNameGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        String newFieldName = "priority";

        // When
        log.setFieldName(newFieldName);

        // Then
        assertEquals(newFieldName, log.getFieldName());
    }

    @Test
    @DisplayName("Should allow null fieldName")
    void testSetNullFieldName() {
        // Given
        ActivityLog log = new ActivityLog();

        // When
        log.setFieldName(null);

        // Then
        assertNull(log.getFieldName());
    }

    @Test
    @DisplayName("Should set and get oldValue")
    void testOldValueGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        String newOldValue = "LOW";

        // When
        log.setOldValue(newOldValue);

        // Then
        assertEquals(newOldValue, log.getOldValue());
    }

    @Test
    @DisplayName("Should handle LOB oldValue with large content")
    void testLargeOldValue() {
        // Given
        ActivityLog log = new ActivityLog();
        String largeContent = "A".repeat(10000); // Large content for LOB

        // When
        log.setOldValue(largeContent);

        // Then
        assertEquals(largeContent, log.getOldValue());
    }

    @Test
    @DisplayName("Should set and get newValue")
    void testNewValueGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        String newNewValue = "HIGH";

        // When
        log.setNewValue(newNewValue);

        // Then
        assertEquals(newNewValue, log.getNewValue());
    }

    @Test
    @DisplayName("Should set and get createdAt")
    void testCreatedAtGetterAndSetter() {
        // Given
        ActivityLog log = new ActivityLog();
        Instant newCreatedAt = Instant.now().plusSeconds(3600);

        // When
        log.setCreatedAt(newCreatedAt);

        // Then
        assertEquals(newCreatedAt, log.getCreatedAt());
    }

    @Test
    @DisplayName("Should throw NullPointerException when setting null createdAt")
    void testSetNullCreatedAt() {
        // Given
        ActivityLog log = new ActivityLog();

        // When & Then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            log.setCreatedAt(null);
        });

        assertTrue(exception.getMessage().contains("createdAt"));
    }

    @Test
    @DisplayName("Should implement equals based only on id")
    void testEquals() {
        // Given
        ActivityLog sameId = new ActivityLog(TEST_ID, testIssue, testUser, testActionType,
            "different", "different", "different", Instant.now());

        ActivityLog differentId = new ActivityLog(2L, testIssue, testUser, testActionType,
            TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT);

        ActivityLog nullId = new ActivityLog(null, testIssue, testUser, testActionType,
            TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT);

        // Then
        assertEquals(activityLog, sameId, "Objects with same ID should be equal regardless of other fields");
        assertNotEquals(activityLog, differentId, "Objects with different IDs should not be equal");
        assertNotEquals(activityLog, nullId, "Objects with null ID should not be equal to objects with ID");
        assertNotEquals(activityLog, null, "Object should not be equal to null");
        assertNotEquals(activityLog, new Object(), "Object should not be equal to different type");

        // Reflexivity
        assertEquals(activityLog, activityLog, "Object should be equal to itself");
    }

    @Test
    @DisplayName("Should implement hashCode based only on id")
    void testHashCode() {
        // Given
        ActivityLog sameId = new ActivityLog(TEST_ID, testIssue, testUser, testActionType,
            "different", "different", "different", Instant.now());

        ActivityLog differentId = new ActivityLog(2L, testIssue, testUser, testActionType,
            TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT);

        // Then
        assertEquals(activityLog.hashCode(), sameId.hashCode(),
                "Objects with same ID should have same hashCode");
        assertNotEquals(activityLog.hashCode(), differentId.hashCode(),
                "Objects with different IDs should have different hashCode");

        // Consistency
        int firstHashCode = activityLog.hashCode();
        int secondHashCode = activityLog.hashCode();
        assertEquals(firstHashCode, secondHashCode, "HashCode should be consistent");
    }

    @Test
    @DisplayName("Should handle null ID in equals and hashCode")
    void testNullIdHandling() {
        // Given
        ActivityLog nullId1 = new ActivityLog(null, testIssue, testUser, testActionType,
            TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT);

        ActivityLog nullId2 = new ActivityLog(null, testIssue, testUser, testActionType,
            TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT);

        // Then - два объекта с null ID не равны друг другу
        assertNotEquals(nullId1, nullId2, "Objects with null IDs should not be equal");
        assertNotEquals(nullId1.hashCode(), nullId2.hashCode(),
                "Objects with null IDs should have different hashCodes");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        // When
        String toString = activityLog.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("id=" + TEST_ID));
        assertTrue(toString.contains("fieldName=" + TEST_FIELD_NAME));
        assertTrue(toString.contains("ActivityLog"));
        // Не должно содержать LOB данные для безопасности
        assertFalse(toString.contains(TEST_OLD_VALUE));
        assertFalse(toString.contains(TEST_NEW_VALUE));
    }

    @Test
    @DisplayName("Should work correctly as JPA entity")
    void testEntityBehavior() {
        // Given
        ActivityLog log = ActivityLog.builder()
                .issue(testIssue)
                .actionType(testActionType)
                .fieldName("description")
                .oldValue("Old desc")
                .newValue("New desc")
                .createdAt(Instant.now())
                .build();

        // When & Then
        assertNotNull(log);
        assertEquals(testIssue, log.getIssue());
        assertEquals(testActionType, log.getActionType());
        assertNull(log.getId()); // ID будет сгенерирован БД

        // Проверяем, что сущность может быть использована в JPA контексте
        log.setId(100L);
        assertEquals(100L, log.getId());
    }

    @Test
    @DisplayName("Should test builder with partial parameters")
    void testPartialBuilder() {
        // When - builder только с обязательными полями
        ActivityLog log = ActivityLog.builder()
                .issue(testIssue)
                .actionType(testActionType)
                .createdAt(TEST_CREATED_AT)
                .build();

        // Then
        assertNotNull(log);
        assertEquals(testIssue, log.getIssue());
        assertEquals(testActionType, log.getActionType());
        assertEquals(TEST_CREATED_AT, log.getCreatedAt());
        assertNull(log.getId());
        assertNull(log.getUser());
        assertNull(log.getFieldName());
        assertNull(log.getOldValue());
        assertNull(log.getNewValue());
    }

    @Test
    @DisplayName("Should test different action types scenarios")
    void testDifferentActionScenarios() {
        // Scenario 1: Status change
        ActivityLog statusChange = ActivityLog.builder()
                .issue(testIssue)
                .actionType(ActionType.builder().id(1L).name("STATUS_CHANGE").build())
                .fieldName("status")
                .oldValue("OPEN")
                .newValue("IN_PROGRESS")
                .createdAt(Instant.now())
                .build();

        // Scenario 2: Assignment (no old/new values)
        ActivityLog assignment = ActivityLog.builder()
                .issue(testIssue)
                .actionType(ActionType.builder().id(2L).name("ASSIGNED").build())
                .user(testUser)
                .createdAt(Instant.now())
                .build();

        // Scenario 3: Comment added (with LOB content)
        ActivityLog comment = ActivityLog.builder()
                .issue(testIssue)
                .actionType(ActionType.builder().id(3L).name("COMMENT_ADDED").build())
                .fieldName("comment")
                .newValue("This is a long comment text...")
                .createdAt(Instant.now())
                .build();

        // Verify all scenarios
        assertNotNull(statusChange);
        assertNotNull(assignment);
        assertNotNull(comment);

        assertEquals("STATUS_CHANGE", statusChange.getActionType().getName());
        assertNull(assignment.getFieldName());
        assertEquals("comment", comment.getFieldName());
    }

    @Test
    @DisplayName("Should verify @NonNull annotation behavior")
    void testNonNullAnnotations() {
        // Test constructor with null issue
        assertThrows(NullPointerException.class, () ->
            new ActivityLog(1L, null, testUser, testActionType,
                TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT));

        // Test constructor with null actionType
        assertThrows(NullPointerException.class, () ->
            new ActivityLog(1L, testIssue, testUser, null,
                TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, TEST_CREATED_AT));

        // Test constructor with null createdAt
        assertThrows(NullPointerException.class, () ->
            new ActivityLog(1L, testIssue, testUser, testActionType,
                TEST_FIELD_NAME, TEST_OLD_VALUE, TEST_NEW_VALUE, null));

        // Test builder with null required fields
        assertThrowsExactly(NullPointerException.class, () ->
            ActivityLog.builder().issue(null).build());

        assertThrowsExactly(NullPointerException.class, () ->
            ActivityLog.builder().actionType(null).build());

        assertThrowsExactly(NullPointerException.class, () ->
            ActivityLog.builder().createdAt(null).build());
    }

    @Test
    @DisplayName("Should test field name length constraint")
    void testFieldNameLength() {
        // Given
        ActivityLog log = new ActivityLog();
        String maxLengthFieldName = "A".repeat(100); // Maximum length

        // When
        log.setFieldName(maxLengthFieldName);

        // Then
        assertEquals(maxLengthFieldName, log.getFieldName());

        // Test exceeding length (though constraint is at DB level)
        String tooLongFieldName = "A".repeat(150);
        log.setFieldName(tooLongFieldName);
        assertEquals(tooLongFieldName, log.getFieldName()); // Java allows it, DB will truncate/error
    }
}
