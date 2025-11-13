package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationQueueTest {

    private User recipientUser;
    private NotificationTemplate template;
    private Issue issue;
    private Project project;
    private NotificationStatus status;

    @BeforeEach
    void setUp() {
        recipientUser = mock(User.class);
        template = mock(NotificationTemplate.class);
        issue = mock(Issue.class);
        project = mock(Project.class);
        status = mock(NotificationStatus.class);
    }

    @Test
    @DisplayName("Should create NotificationQueue with builder pattern")
    void shouldCreateNotificationQueueWithBuilder() {
        // Given
        Instant now = Instant.now();
        String jsonPayload = "{\"key\": \"value\", \"number\": 123}";

        // When
        NotificationQueue queue = NotificationQueue.builder()
                .id(1L)
                .recipientUser(recipientUser)
                .recipientEmail("user@example.com")
                .template(template)
                .issue(issue)
                .project(project)
                .payload(jsonPayload)
                .status(status)
                .sentAt(now)
                .failedAttempts(2)
                .createdAt(now)
                .build();

        // Then
        assertNotNull(queue);
        assertEquals(1L, queue.getId());
        assertEquals(recipientUser, queue.getRecipientUser());
        assertEquals("user@example.com", queue.getRecipientEmail());
        assertEquals(template, queue.getTemplate());
        assertEquals(issue, queue.getIssue());
        assertEquals(project, queue.getProject());
        assertEquals(jsonPayload, queue.getPayload());
        assertEquals(status, queue.getStatus());
        assertEquals(now, queue.getSentAt());
        assertEquals(2, queue.getFailedAttempts());
        assertEquals(now, queue.getCreatedAt());
    }

    @Test
    @DisplayName("Should create NotificationQueue with no-args constructor")
    void shouldCreateNotificationQueueWithNoArgsConstructor() {
        // When
        NotificationQueue queue = new NotificationQueue();

        // Then
        assertNotNull(queue);
        assertNull(queue.getId());
        assertNull(queue.getRecipientUser());
        assertNull(queue.getRecipientEmail());
        assertNull(queue.getTemplate());
        assertNull(queue.getIssue());
        assertNull(queue.getProject());
        assertNull(queue.getPayload());
        assertNull(queue.getStatus());
        assertNull(queue.getSentAt());
        assertEquals(0, queue.getFailedAttempts());
        assertNull(queue.getCreatedAt());
    }

    @Test
    @DisplayName("Should create NotificationQueue with all-args constructor")
    void shouldCreateNotificationQueueWithAllArgsConstructor() {
        // Given
        Instant now = Instant.now();
        String jsonPayload = "{\"notification\": \"test\"}";

        // When
        NotificationQueue queue = new NotificationQueue(
                1L, recipientUser, "test@example.com", template,
                issue, project, jsonPayload, status, now, 1, now
        );

        // Then
        assertNotNull(queue);
        assertEquals(1L, queue.getId());
        assertEquals("test@example.com", queue.getRecipientEmail());
        assertEquals(jsonPayload, queue.getPayload());
        assertEquals(1, queue.getFailedAttempts());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        NotificationQueue queue = new NotificationQueue();
        Instant sentAt = Instant.now().plus(1, ChronoUnit.HOURS);
        Instant createdAt = Instant.now();
        String payload = "{\"action\": \"created\", \"user\": \"john_doe\"}";

        // When
        queue.setId(100L);
        queue.setRecipientUser(recipientUser);
        queue.setRecipientEmail("recipient@company.com");
        queue.setTemplate(template);
        queue.setIssue(issue);
        queue.setProject(project);
        queue.setPayload(payload);
        queue.setStatus(status);
        queue.setSentAt(sentAt);
        queue.setFailedAttempts(3);
        queue.setCreatedAt(createdAt);

        // Then
        assertEquals(100L, queue.getId());
        assertEquals(recipientUser, queue.getRecipientUser());
        assertEquals("recipient@company.com", queue.getRecipientEmail());
        assertEquals(template, queue.getTemplate());
        assertEquals(issue, queue.getIssue());
        assertEquals(project, queue.getProject());
        assertEquals(payload, queue.getPayload());
        assertEquals(status, queue.getStatus());
        assertEquals(sentAt, queue.getSentAt());
        assertEquals(3, queue.getFailedAttempts());
        assertEquals(createdAt, queue.getCreatedAt());
    }

    @Test
    @DisplayName("Should handle null values in optional fields")
    void shouldHandleNullValuesInOptionalFields() {
        // When
        NotificationQueue queue = NotificationQueue.builder()
                .id(1L)
                .status(status) // Only required field
                .failedAttempts(0)
                .build();

        // Then
        assertNotNull(queue);
        assertEquals(1L, queue.getId());
        assertNull(queue.getRecipientUser());
        assertNull(queue.getRecipientEmail());
        assertNull(queue.getTemplate());
        assertNull(queue.getIssue());
        assertNull(queue.getProject());
        assertNull(queue.getPayload());
        assertNotNull(queue.getStatus());
        assertNull(queue.getSentAt());
        assertEquals(0, queue.getFailedAttempts());
        assertNull(queue.getCreatedAt());
    }

    @Test
    @DisplayName("Should have default value for failedAttempts")
    void shouldHaveDefaultValueForFailedAttempts() {
        // When
        NotificationQueue queue = new NotificationQueue();

        // Then
        assertEquals(0, queue.getFailedAttempts());

        // When - builder without explicit failedAttempts
        NotificationQueue queue2 = NotificationQueue.builder()
                .status(status)
                .build();

        // Then
        assertEquals(0, queue2.getFailedAttempts());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id")
    void shouldImplementEqualsAndHashCodeBasedOnId() {
        // Given
        NotificationQueue queue1 = NotificationQueue.builder()
                .id(1L)
                .recipientEmail("email1@test.com")
                .status(status)
                .build();

        NotificationQueue queue2 = NotificationQueue.builder()
                .id(1L)
                .recipientEmail("email2@test.com") // Different email
                .status(status)
                .build();

        NotificationQueue queue3 = NotificationQueue.builder()
                .id(2L) // Different ID
                .recipientEmail("email1@test.com")
                .status(status)
                .build();

        NotificationQueue queue4 = new NotificationQueue(); // null ID

        // Then
        // Same ID should be equal regardless of other fields
        assertEquals(queue1, queue2);
        assertEquals(queue1.hashCode(), queue2.hashCode());

        // Different ID should not be equal
        assertNotEquals(queue1, queue3);
        assertNotEquals(queue1.hashCode(), queue3.hashCode());

        // Null ID comparison
        assertNotEquals(queue1, queue4);
        assertNotEquals(queue1, null);
        assertNotEquals(queue1, "some string");

        // Reflexivity
        assertEquals(queue1, queue1);

        // Consistency
        assertEquals(queue1.hashCode(), queue1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        NotificationQueue queue1 = NotificationQueue.builder().id(1L).status(status).build();
        NotificationQueue queue2 = NotificationQueue.builder().id(1L).status(status).build(); // Same ID
        NotificationQueue queue3 = NotificationQueue.builder().id(2L).status(status).build(); // Different ID

        java.util.Set<NotificationQueue> set = new java.util.HashSet<>();

        // When
        set.add(queue1);
        set.add(queue2); // Duplicate by ID
        set.add(queue3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by ID");
        assertTrue(set.contains(queue1));
        assertTrue(set.contains(queue2));
        assertTrue(set.contains(queue3));
    }

    @Test
    @DisplayName("Should handle JSON payload correctly")
    void shouldHandleJsonPayloadCorrectly() {
        // Given
        String complexJson = """
            {
                "issueKey": "PROJ-123",
                "oldStatus": "Open",
                "newStatus": "In Progress",
                "changedBy": "john_doe",
                "timestamp": "2024-01-15T10:30:00Z"
            }
            """;

        // When
        NotificationQueue queue = NotificationQueue.builder()
                .id(1L)
                .status(status)
                .payload(complexJson)
                .build();

        // Then
        assertNotNull(queue.getPayload());
        assertTrue(queue.getPayload().contains("PROJ-123"));
        assertTrue(queue.getPayload().contains("oldStatus"));
    }

    @Test
    @DisplayName("Should handle null payload")
    void shouldHandleNullPayload() {
        // When
        NotificationQueue queue = NotificationQueue.builder()
                .id(1L)
                .status(status)
                .payload(null)
                .build();

        // Then
        assertNull(queue.getPayload());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        NotificationQueue queue = NotificationQueue.builder()
                .id(1L)
                .recipientEmail("test@example.com")
                .status(status)
                .failedAttempts(0)
                .build();

        // When
        String toStringResult = queue.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain some identifiable information
        assertTrue(toStringResult.contains("NotificationQueue") ||
                  toStringResult.contains("id=1") ||
                  toStringResult.contains("test@example.com"));
    }

    @Test
    @DisplayName("Should handle timestamp fields correctly")
    void shouldHandleTimestampFieldsCorrectly() {
        // Given
        Instant sentAt = Instant.parse("2024-01-15T10:30:00Z");
        Instant createdAt = Instant.parse("2024-01-15T10:00:00Z");

        // When
        NotificationQueue queue = NotificationQueue.builder()
                .id(1L)
                .status(status)
                .sentAt(sentAt)
                .createdAt(createdAt)
                .build();

        // Then
        assertEquals(sentAt, queue.getSentAt());
        assertEquals(createdAt, queue.getCreatedAt());
        assertTrue(queue.getSentAt().isAfter(queue.getCreatedAt()));
    }

    @Test
    @DisplayName("Should handle recipient preference (user vs email)")
    void shouldHandleRecipientPreference() {
        // Case 1: Only recipient user
        NotificationQueue queue1 = NotificationQueue.builder()
                .id(1L)
                .recipientUser(recipientUser)
                .status(status)
                .build();

        // Case 2: Only recipient email
        NotificationQueue queue2 = NotificationQueue.builder()
                .id(2L)
                .recipientEmail("external@company.com")
                .status(status)
                .build();

        // Case 3: Both provided
        NotificationQueue queue3 = NotificationQueue.builder()
                .id(3L)
                .recipientUser(recipientUser)
                .recipientEmail("backup@company.com")
                .status(status)
                .build();

        // Then
        assertNotNull(queue1.getRecipientUser());
        assertNull(queue1.getRecipientEmail());

        assertNull(queue2.getRecipientUser());
        assertNotNull(queue2.getRecipientEmail());

        assertNotNull(queue3.getRecipientUser());
        assertNotNull(queue3.getRecipientEmail());
    }

    @Test
    @DisplayName("Should increment failed attempts")
    void shouldIncrementFailedAttempts() {
        // Given
        NotificationQueue queue = NotificationQueue.builder()
                .id(1L)
                .status(status)
                .failedAttempts(2)
                .build();

        // When
        queue.setFailedAttempts(queue.getFailedAttempts() + 1);

        // Then
        assertEquals(3, queue.getFailedAttempts());
    }
}
