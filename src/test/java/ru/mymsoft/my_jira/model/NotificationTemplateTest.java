package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTemplateTest {

    private Instant fixedTimestamp;

    @BeforeEach
    void setUp() {
        fixedTimestamp = Instant.parse("2024-01-15T10:00:00Z");
    }

    @Test
    @DisplayName("Should create NotificationTemplate with builder pattern")
    void shouldCreateNotificationTemplateWithBuilder() {
        // Given
        String subjectTemplate = "Issue {{issueKey}} has been updated";
        String bodyTemplate = "Dear {{user}}, issue {{issueKey}} has been updated from {{oldStatus}} to {{newStatus}}.";

        // When
        NotificationTemplate template = NotificationTemplate.builder()
                .id(1L)
                .name("ISSUE_STATUS_CHANGED_EMAIL")
                .subjectTemplate(subjectTemplate)
                .bodyTemplate(bodyTemplate)
                .templateType("EMAIL")
                .isActive(true)
                .createdAt(fixedTimestamp)
                .updatedAt(fixedTimestamp.plus(1, ChronoUnit.HOURS))
                .build();

        // Then
        assertNotNull(template);
        assertEquals(1L, template.getId());
        assertEquals("ISSUE_STATUS_CHANGED_EMAIL", template.getName());
        assertEquals(subjectTemplate, template.getSubjectTemplate());
        assertEquals(bodyTemplate, template.getBodyTemplate());
        assertEquals("EMAIL", template.getTemplateType());
        assertTrue(template.getIsActive());
        assertEquals(fixedTimestamp, template.getCreatedAt());
        assertEquals(fixedTimestamp.plus(1, ChronoUnit.HOURS), template.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create NotificationTemplate with no-args constructor")
    void shouldCreateNotificationTemplateWithNoArgsConstructor() {
        // When
        NotificationTemplate template = new NotificationTemplate();

        // Then
        assertNotNull(template);
        assertNull(template.getId());
        assertNull(template.getName());
        assertNull(template.getSubjectTemplate());
        assertNull(template.getBodyTemplate());
        assertNull(template.getTemplateType());
        assertTrue(template.getIsActive()); // Default value
        assertNull(template.getCreatedAt());
        assertNull(template.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create NotificationTemplate with all-args constructor")
    void shouldCreateNotificationTemplateWithAllArgsConstructor() {
        // Given
        Instant createdAt = fixedTimestamp;
        Instant updatedAt = fixedTimestamp.plus(30, ChronoUnit.MINUTES);

        // When
        NotificationTemplate template = new NotificationTemplate(
                1L,
                "COMMENT_ADDED_IN_APP",
                "New comment on {{issueKey}}",
                "User {{author}} added a new comment: {{comment}}",
                "IN_APP",
                false,
                createdAt,
                updatedAt
        );

        // Then
        assertNotNull(template);
        assertEquals(1L, template.getId());
        assertEquals("COMMENT_ADDED_IN_APP", template.getName());
        assertEquals("New comment on {{issueKey}}", template.getSubjectTemplate());
        assertEquals("User {{author}} added a new comment: {{comment}}", template.getBodyTemplate());
        assertEquals("IN_APP", template.getTemplateType());
        assertFalse(template.getIsActive());
        assertEquals(createdAt, template.getCreatedAt());
        assertEquals(updatedAt, template.getUpdatedAt());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllProperties() {
        // Given
        NotificationTemplate template = new NotificationTemplate();
        Instant now = Instant.now();
        Instant later = now.plus(1, ChronoUnit.HOURS);

        // When
        template.setId(5L);
        template.setName("ISSUE_CREATED_SLACK");
        template.setSubjectTemplate("New issue: {{issueKey}}");
        template.setBodyTemplate("Issue {{issueKey}} created by {{reporter}}");
        template.setTemplateType("SLACK");
        template.setIsActive(true);
        template.setCreatedAt(now);
        template.setUpdatedAt(later);

        // Then
        assertEquals(5L, template.getId());
        assertEquals("ISSUE_CREATED_SLACK", template.getName());
        assertEquals("New issue: {{issueKey}}", template.getSubjectTemplate());
        assertEquals("Issue {{issueKey}} created by {{reporter}}", template.getBodyTemplate());
        assertEquals("SLACK", template.getTemplateType());
        assertTrue(template.getIsActive());
        assertEquals(now, template.getCreatedAt());
        assertEquals(later, template.getUpdatedAt());
    }

    @Test
    @DisplayName("Should have default value for isActive field")
    void shouldHaveDefaultValueForIsActive() {
        // When - using no-args constructor
        NotificationTemplate template = new NotificationTemplate();

        // Then
        assertTrue(template.getIsActive());

        // When - using builder without explicit isActive
        NotificationTemplate template2 = NotificationTemplate.builder()
                .name("TEST_TEMPLATE")
                .bodyTemplate("Body content")
                .templateType("EMAIL")
                .build();

        // Then
        assertTrue(template2.getIsActive());
    }

    @Test
    @DisplayName("Should implement equals and hashCode based on id and name")
    void shouldImplementEqualsAndHashCodeBasedOnIdAndName() {
        // Given
        NotificationTemplate template1 = NotificationTemplate.builder()
                .id(1L)
                .name("TEMPLATE_A")
                .bodyTemplate("Body A")
                .templateType("EMAIL")
                .isActive(true)
                .build();

        NotificationTemplate template2 = NotificationTemplate.builder()
                .id(1L)
                .name("TEMPLATE_A")
                .bodyTemplate("Different Body") // Different body
                .templateType("SLACK") // Different type
                .isActive(false) // Different active status
                .build();

        NotificationTemplate template3 = NotificationTemplate.builder()
                .id(2L) // Different ID
                .name("TEMPLATE_A")
                .bodyTemplate("Body A")
                .templateType("EMAIL")
                .build();

        NotificationTemplate template4 = NotificationTemplate.builder()
                .id(1L)
                .name("TEMPLATE_B") // Different name
                .bodyTemplate("Body A")
                .templateType("EMAIL")
                .build();

        NotificationTemplate template5 = new NotificationTemplate(); // null fields

        // Then
        // Same ID and name should be equal regardless of other fields
        assertEquals(template1, template2);
        assertEquals(template1.hashCode(), template2.hashCode());

        // Different ID should not be equal
        assertNotEquals(template1, template3);

        // Different name should not be equal
        assertNotEquals(template1, template4);

        // Null comparison
        assertNotEquals(template1, null);
        assertNotEquals(template1, "some string");
        assertNotEquals(template1, template5);

        // Reflexivity
        assertEquals(template1, template1);

        // Symmetry
        assertEquals(template1, template2);
        assertEquals(template2, template1);

        // Consistency
        assertEquals(template1.hashCode(), template1.hashCode());
    }

    @Test
    @DisplayName("Should work correctly in collections")
    void shouldWorkCorrectlyInCollections() {
        // Given
        NotificationTemplate template1 = NotificationTemplate.builder()
                .id(1L)
                .name("TEMPLATE_1")
                .bodyTemplate("Body 1")
                .templateType("EMAIL")
                .build();

        NotificationTemplate template2 = NotificationTemplate.builder()
                .id(1L)
                .name("TEMPLATE_1")
                .bodyTemplate("Body 2") // Different body
                .templateType("SLACK") // Different type
                .build(); // Equal to template1 (same id and name)

        NotificationTemplate template3 = NotificationTemplate.builder()
                .id(2L)
                .name("TEMPLATE_2")
                .bodyTemplate("Body 3")
                .templateType("EMAIL")
                .build(); // Different

        Set<NotificationTemplate> set = new HashSet<>();

        // When
        set.add(template1);
        set.add(template2); // Duplicate (same id and name)
        set.add(template3);

        // Then
        assertEquals(2, set.size(), "Set should not contain duplicates by id and name");
        assertTrue(set.contains(template1));
        assertTrue(set.contains(template2));
        assertTrue(set.contains(template3));
    }

    @Test
    @DisplayName("Should handle null ID and name in equals and hashCode")
    void shouldHandleNullIdAndNameInEqualsAndHashCode() {
        // Given
        NotificationTemplate template1 = new NotificationTemplate();
        template1.setBodyTemplate("Body content");
        template1.setTemplateType("EMAIL");

        NotificationTemplate template2 = new NotificationTemplate();
        template2.setBodyTemplate("Body content");
        template2.setTemplateType("EMAIL");

        NotificationTemplate template3 = new NotificationTemplate();
        template3.setName("SOME_NAME"); // Only name set
        template3.setBodyTemplate("Body content");
        template3.setTemplateType("EMAIL");

        // Then
        assertEquals(template1, template2);
        assertNotEquals(template1, template3);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    @DisplayName("Should handle null and empty names")
    void shouldHandleNullAndEmptyNames(String name) {
        // Given
        NotificationTemplate template = new NotificationTemplate();

        // When
        template.setName(name);

        // Then
        assertEquals(name, template.getName());
    }

    @Test
    @DisplayName("Should handle LOB fields for subject and body templates")
    void shouldHandleLOBFieldsForSubjectAndBodyTemplates() {
        // Given - Large content that would typically be stored as LOB
        String longSubject = "Very long subject template ".repeat(50);
        String longBody = "Very long body template with lots of content and placeholders ".repeat(100);

        // When
        NotificationTemplate template = NotificationTemplate.builder()
                .id(1L)
                .name("LONG_TEMPLATE")
                .subjectTemplate(longSubject)
                .bodyTemplate(longBody)
                .templateType("EMAIL")
                .isActive(true)
                .build();

        // Then
        assertEquals(longSubject, template.getSubjectTemplate());
        assertEquals(longBody, template.getBodyTemplate());
        assertTrue(template.getSubjectTemplate().length() > 100);
        assertTrue(template.getBodyTemplate().length() > 1000);
    }

    @Test
    @DisplayName("Should handle null subject template")
    void shouldHandleNullSubjectTemplate() {
        // When
        NotificationTemplate template = NotificationTemplate.builder()
                .id(1L)
                .name("NO_SUBJECT_TEMPLATE")
                .subjectTemplate(null) // Subject can be null
                .bodyTemplate("Body is required") // Body cannot be null in DB
                .templateType("IN_APP")
                .build();

        // Then
        assertNull(template.getSubjectTemplate());
        assertNotNull(template.getBodyTemplate());
    }

    @Test
    @DisplayName("Should test toString method")
    void shouldTestToStringMethod() {
        // Given
        NotificationTemplate template = NotificationTemplate.builder()
                .id(1L)
                .name("TEST_TEMPLATE")
                .subjectTemplate("Test Subject")
                .bodyTemplate("Test Body")
                .templateType("EMAIL")
                .isActive(true)
                .build();

        // When
        String toStringResult = template.toString();

        // Then
        assertNotNull(toStringResult);
        assertFalse(toStringResult.isEmpty());
        // Should contain class name and some identifiable information
        assertTrue(toStringResult.contains("NotificationTemplate") ||
                  toStringResult.contains("TEST_TEMPLATE") ||
                  toStringResult.contains("EMAIL"));
    }

    @Test
    @DisplayName("Should handle different template types")
    void shouldHandleDifferentTemplateTypes() {
        // Given
        String[] templateTypes = {"EMAIL", "SLACK", "IN_APP", "SMS", "WEBHOOK"};

        // When & Then
        for (int i = 0; i < templateTypes.length; i++) {
            NotificationTemplate template = NotificationTemplate.builder()
                    .id((long) (i + 1))
                    .name("TEMPLATE_" + templateTypes[i])
                    .bodyTemplate("Body for " + templateTypes[i])
                    .templateType(templateTypes[i])
                    .build();

            assertEquals(templateTypes[i], template.getTemplateType());
            assertEquals("TEMPLATE_" + templateTypes[i], template.getName());
        }
    }

    @Test
    @DisplayName("Should verify timestamp ordering")
    void shouldVerifyTimestampOrdering() {
        // Given
        Instant createdAt = fixedTimestamp;
        Instant updatedAt = fixedTimestamp.plus(1, ChronoUnit.DAYS);

        // When
        NotificationTemplate template = NotificationTemplate.builder()
                .id(1L)
                .name("TIMESTAMP_TEST")
                .bodyTemplate("Body content")
                .templateType("EMAIL")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Then
        assertTrue(template.getUpdatedAt().isAfter(template.getCreatedAt()));
    }

    @Test
    @DisplayName("Should handle template activation and deactivation")
    void shouldHandleTemplateActivationAndDeactivation() {
        // Given
        NotificationTemplate template = NotificationTemplate.builder()
                .id(1L)
                .name("TOGGLE_TEMPLATE")
                .bodyTemplate("Body content")
                .templateType("EMAIL")
                .isActive(true)
                .build();

        // When - deactivate
        template.setIsActive(false);

        // Then
        assertFalse(template.getIsActive());

        // When - reactivate
        template.setIsActive(true);

        // Then
        assertTrue(template.getIsActive());
    }

    @Test
    @DisplayName("Should create template with only required fields")
    void shouldCreateTemplateWithOnlyRequiredFields() {
        // When
        NotificationTemplate template = NotificationTemplate.builder()
                .name("MINIMAL_TEMPLATE")
                .bodyTemplate("Required body content")
                .templateType("EMAIL")
                .build();

        // Then
        assertNotNull(template);
        assertEquals("MINIMAL_TEMPLATE", template.getName());
        assertEquals("Required body content", template.getBodyTemplate());
        assertEquals("EMAIL", template.getTemplateType());
        assertTrue(template.getIsActive()); // Default value
        assertNull(template.getId());
        assertNull(template.getSubjectTemplate());
        assertNull(template.getCreatedAt());
        assertNull(template.getUpdatedAt());
    }

    @Test
    @DisplayName("Should handle template with HTML content")
    void shouldHandleTemplateWithHTMLContent() {
        // Given
        String htmlBody = """
            <html>
                <body>
                    <h1>Issue Update</h1>
                    <p>Issue <strong>{{issueKey}}</strong> has been updated.</p>
                    <p>New status: <span style="color: green">{{newStatus}}</span></p>
                </body>
            </html>
            """;

        // When
        NotificationTemplate template = NotificationTemplate.builder()
                .id(1L)
                .name("HTML_EMAIL_TEMPLATE")
                .subjectTemplate("HTML: Issue Update")
                .bodyTemplate(htmlBody)
                .templateType("EMAIL")
                .isActive(true)
                .build();

        // Then
        assertNotNull(template);
        assertTrue(template.getBodyTemplate().contains("<html>"));
        assertTrue(template.getBodyTemplate().contains("</body>"));
        assertTrue(template.getBodyTemplate().contains("{{issueKey}}"));
    }
}
