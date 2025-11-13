package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.Instant;
import static org.assertj.core.api.Assertions.assertThat;

class ActivityLogTest {

    private ActivityLog activityLog;
    private Issue issue;
    private User user;

    @BeforeEach
    void setUp() {
        issue = new Issue();
        issue.setId(1L);

        user = new User();
        user.setId(1L);

        activityLog = new ActivityLog();
        activityLog.setId(1L);
        activityLog.setIssue(issue);
        activityLog.setUser(user);
        activityLog.setActionType("CREATE");
        activityLog.setFieldName("status");
        activityLog.setOldValue("TODO");
        activityLog.setNewValue("IN_PROGRESS");
        activityLog.setCreatedAt(Instant.now());
    }

    @Test
    void testActivityLogCreation() {
        // Then
        assertThat(activityLog.getId()).isEqualTo(1L);
        assertThat(activityLog.getIssue()).isEqualTo(issue);
        assertThat(activityLog.getUser()).isEqualTo(user);
        assertThat(activityLog.getActionType()).isEqualTo("CREATE");
        assertThat(activityLog.getFieldName()).isEqualTo("status");
        assertThat(activityLog.getOldValue()).isEqualTo("TODO");
        assertThat(activityLog.getNewValue()).isEqualTo("IN_PROGRESS");
        assertThat(activityLog.getCreatedAt()).isNotNull();
    }

    @Test
    void testActivityLogWithNullUser() {
        // Given
        activityLog.setUser(null);

        // Then
        assertThat(activityLog.getUser()).isNull();
        assertThat(activityLog.getActionType()).isEqualTo("CREATE");
        assertThat(activityLog.getIssue()).isNotNull();
    }

    @Test
    void testActivityLogWithLobFields() {
        // Given
        String longText = "A".repeat(1000);
        activityLog.setOldValue(longText);
        activityLog.setNewValue("B".repeat(2000));

        // Then
        assertThat(activityLog.getOldValue()).hasSize(1000);
        assertThat(activityLog.getNewValue()).hasSize(2000);
        assertThat(activityLog.getOldValue()).startsWith("A");
        assertThat(activityLog.getNewValue()).startsWith("B");
    }

    @Test
    void testActivityLogWithNullFieldName() {
        // Given
        activityLog.setFieldName(null);

        // Then
        assertThat(activityLog.getFieldName()).isNull();
        assertThat(activityLog.getActionType()).isEqualTo("CREATE");
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        ActivityLog log1 = new ActivityLog();
        log1.setId(1L);

        ActivityLog log2 = new ActivityLog();
        log2.setId(1L); // Тот же ID

        ActivityLog log3 = new ActivityLog();
        log3.setId(2L); // Другой ID

        // Then
        assertThat(log1)
          .isEqualTo(log2)
          .isNotEqualTo(log3)
          .hasSameHashCodeAs(log2);
    }

    @Test
    void testNoArgsConstructor() {
        // Given
        ActivityLog emptyLog = new ActivityLog();

        // Then
        assertThat(emptyLog.getId()).isNull();
        assertThat(emptyLog.getIssue()).isNull();
        assertThat(emptyLog.getUser()).isNull();
        assertThat(emptyLog.getActionType()).isNull();
        assertThat(emptyLog.getFieldName()).isNull();
        assertThat(emptyLog.getOldValue()).isNull();
        assertThat(emptyLog.getNewValue()).isNull();
        assertThat(emptyLog.getCreatedAt()).isNull();
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Instant now = Instant.now();
        ActivityLog log = new ActivityLog(1L, issue, user, "UPDATE", "priority", "LOW", "HIGH", now);

        // Then
        assertThat(log.getId()).isEqualTo(1L);
        assertThat(log.getIssue()).isEqualTo(issue);
        assertThat(log.getUser()).isEqualTo(user);
        assertThat(log.getActionType()).isEqualTo("UPDATE");
        assertThat(log.getFieldName()).isEqualTo("priority");
        assertThat(log.getOldValue()).isEqualTo("LOW");
        assertThat(log.getNewValue()).isEqualTo("HIGH");
        assertThat(log.getCreatedAt()).isEqualTo(now);
    }

    @Test
    void testCreationTimestampSimulation() {
        // Given
        ActivityLog log = new ActivityLog();

        // When
        Instant before = Instant.now();
        log.setCreatedAt(Instant.now());
        Instant after = Instant.now();

        // Then
        assertThat(log.getCreatedAt()).isBetween(before, after);
    }

    @Test
    void testActivityLogWithEmptyStrings() {
        // Given
        activityLog.setFieldName("");
        activityLog.setOldValue("");
        activityLog.setNewValue("");

        // Then
        assertThat(activityLog.getFieldName()).isEmpty();
        assertThat(activityLog.getOldValue()).isEmpty();
        assertThat(activityLog.getNewValue()).isEmpty();
    }

    @Test
    void testDifferentActionTypes() {
        // Given
        String[] actionTypes = { "CREATE", "UPDATE", "DELETE", "COMMENT", "ASSIGN" };

        for (String actionType : actionTypes) {
            ActivityLog log = new ActivityLog();
            log.setActionType(actionType);

            // Then
            assertThat(log.getActionType()).isEqualTo(actionType);
        }
    }
}
