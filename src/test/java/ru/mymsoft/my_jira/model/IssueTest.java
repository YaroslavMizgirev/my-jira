package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IssueTest {

    private Issue issue;
    private Validator validator;
    private User reporter;
    private Project project;
    private Instant testInstant;

    @BeforeEach
    void setUp() {
        testInstant = Instant.now();

        reporter = new User();
        reporter.setId(1L);
        reporter.setUsername("reporter");

        project = new Project();
        project.setId(1L);
        project.setKey("PROJ");

        issue = new Issue();
        issue.setId(1L);
        issue.setCreatedAt(testInstant);
        issue.setDescription("Test issue description");
        issue.setKey("PROJ-1");
        issue.setTitle("Test Issue");
        issue.setUpdatedAt(testInstant);
        issue.setReporter(reporter);
        issue.setProject(project);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testIssueCreation() {
        assertNotNull(issue);
        assertThat(issue.getId()).isEqualTo(1L);
        assertThat(issue.getKey()).isEqualTo("PROJ-1");
        assertThat(issue.getTitle()).isEqualTo("Test Issue");
        assertThat(issue.getDescription()).isEqualTo("Test issue description");
        assertThat(issue.getCreatedAt()).isEqualTo(testInstant);
        assertThat(issue.getUpdatedAt()).isEqualTo(testInstant);
        assertThat(issue.getReporter()).isEqualTo(reporter);
        assertThat(issue.getProject()).isEqualTo(project);
        assertNull(issue.getAssignee());
        assertNull(issue.getIssueType());
        assertNull(issue.getPriority());
        assertNull(issue.getStatus());
        assertNull(issue.getWorkflow());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Issue newIssue = new Issue();
        User assignee = new User();
        assignee.setId(2L);
        IssueType issueType = new IssueType();
        issueType.setId(1L);
        Priority priority = new Priority();
        priority.setId(1L);
        IssueStatus status = new IssueStatus();
        status.setId(1L);
        Workflow workflow = new Workflow();
        workflow.setId(1L);

        // When
        newIssue.setId(2L);
        newIssue.setKey("PROJ-2");
        newIssue.setTitle("New Issue");
        newIssue.setDescription("New description");
        newIssue.setCreatedAt(testInstant.minusSeconds(3600));
        newIssue.setUpdatedAt(testInstant);
        newIssue.setReporter(reporter);
        newIssue.setAssignee(assignee);
        newIssue.setIssueType(issueType);
        newIssue.setPriority(priority);
        newIssue.setStatus(status);
        newIssue.setWorkflow(workflow);
        newIssue.setProject(project);

        // Then
        assertThat(newIssue.getId()).isEqualTo(2L);
        assertThat(newIssue.getKey()).isEqualTo("PROJ-2");
        assertThat(newIssue.getTitle()).isEqualTo("New Issue");
        assertThat(newIssue.getDescription()).isEqualTo("New description");
        assertThat(newIssue.getReporter()).isEqualTo(reporter);
        assertThat(newIssue.getAssignee()).isEqualTo(assignee);
        assertThat(newIssue.getIssueType()).isEqualTo(issueType);
        assertThat(newIssue.getPriority()).isEqualTo(priority);
        assertThat(newIssue.getStatus()).isEqualTo(status);
        assertThat(newIssue.getWorkflow()).isEqualTo(workflow);
        assertThat(newIssue.getProject()).isEqualTo(project);
    }

    @Test
    void testNoArgsConstructor() {
        Issue emptyIssue = new Issue();

        assertNotNull(emptyIssue);
        assertNull(emptyIssue.getId());
        assertNull(emptyIssue.getKey());
        assertNull(emptyIssue.getTitle());
        assertNull(emptyIssue.getDescription());
        assertNull(emptyIssue.getCreatedAt());
        assertNull(emptyIssue.getUpdatedAt());
        assertNull(emptyIssue.getReporter());
        assertNull(emptyIssue.getAssignee());
        assertNull(emptyIssue.getIssueType());
        assertNull(emptyIssue.getPriority());
        assertNull(emptyIssue.getStatus());
        assertNull(emptyIssue.getWorkflow());
        assertNull(emptyIssue.getProject());
    }

    @Test
    void testAllArgsConstructor() {
        Issue fullIssue = new Issue(1L, testInstant, "Description", "PROJ-1", "Title",
                testInstant, null, null, null, null, reporter, null, project);

        assertThat(fullIssue.getId()).isEqualTo(1L);
        assertThat(fullIssue.getKey()).isEqualTo("PROJ-1");
        assertThat(fullIssue.getTitle()).isEqualTo("Title");
        assertThat(fullIssue.getDescription()).isEqualTo("Description");
        assertThat(fullIssue.getReporter()).isEqualTo(reporter);
        assertThat(fullIssue.getProject()).isEqualTo(project);
    }

    @Test
    void testEqualsAndHashCode() {
        Issue issue1 = new Issue(1L, testInstant, "Desc", "PROJ-1", "Title", testInstant,
                null, null, null, null, reporter, null, project);
        Issue issue2 = new Issue(1L, testInstant, "Desc", "PROJ-1", "Title", testInstant,
                null, null, null, null, reporter, null, project);
        Issue issue3 = new Issue(2L, testInstant, "Desc", "PROJ-2", "Title", testInstant,
                null, null, null, null, reporter, null, project);
        Issue issue4 = new Issue(1L, testInstant, "Desc", "PROJ-3", "Title", testInstant,
                null, null, null, null, reporter, null, project);

        // Test equals
        assertThat(issue1)
            .isEqualTo(issue2)
            .isNotEqualTo(issue3)
            .isNotEqualTo(issue4) // Different key
            .isNotEqualTo(null)
            .isNotEqualTo("string")
            .hasSameHashCodeAs(issue2);
        assertThat(issue1.hashCode()).isNotEqualTo(issue3.hashCode());
        assertThat(issue1.hashCode()).isNotEqualTo(issue4.hashCode());
    }

    @Test
    void testToString() {
        String toString = issue.toString();

        assertThat(toString)
            .contains("Issue")
            .contains("id=1")
            .contains("key=PROJ-1")
            .contains("title=Test Issue");
    }

    @ParameterizedTest
    @ValueSource(strings = {"PROJ-1", "TEST-123", "ABC-999", "PRJ-1A"})
    void testValidIssueKeys(String validKey) {
        Issue validIssue = new Issue();
        validIssue.setKey(validKey);
        validIssue.setTitle("Valid Title");
        validIssue.setReporter(reporter);
        validIssue.setProject(project);

        Set<ConstraintViolation<Issue>> violations = validator.validate(validIssue);

        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidIssueKey(String invalidKey) {
        Issue invalidIssue = new Issue();
        invalidIssue.setKey(invalidKey);
        invalidIssue.setTitle("Valid Title");
        invalidIssue.setReporter(reporter);
        invalidIssue.setProject(project);

        Set<ConstraintViolation<Issue>> violations = validator.validate(invalidIssue);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).contains("key");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void testInvalidIssueTitle(String invalidTitle) {
        Issue invalidIssue = new Issue();
        invalidIssue.setKey("PROJ-1");
        invalidIssue.setTitle(invalidTitle);
        invalidIssue.setReporter(reporter);
        invalidIssue.setProject(project);

        Set<ConstraintViolation<Issue>> violations = validator.validate(invalidIssue);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).contains("title");
    }

    @Test
    void testNullReporterValidation() {
        Issue invalidIssue = new Issue();
        invalidIssue.setKey("PROJ-1");
        invalidIssue.setTitle("Valid Title");
        invalidIssue.setProject(project);
        // reporter is null

        Set<ConstraintViolation<Issue>> violations = validator.validate(invalidIssue);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).contains("reporter");
    }

    @Test
    void testNullProjectValidation() {
        Issue invalidIssue = new Issue();
        invalidIssue.setKey("PROJ-1");
        invalidIssue.setTitle("Valid Title");
        invalidIssue.setReporter(reporter);
        // project is null

        Set<ConstraintViolation<Issue>> violations = validator.validate(invalidIssue);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).contains("project");
    }

    @Test
    void testLobDescriptionHandling() {
        String veryLongDescription = "A".repeat(10000);
        Issue issueWithLongDescription = new Issue();
        issueWithLongDescription.setKey("PROJ-1");
        issueWithLongDescription.setTitle("Title");
        issueWithLongDescription.setDescription(veryLongDescription);
        issueWithLongDescription.setReporter(reporter);
        issueWithLongDescription.setProject(project);

        Set<ConstraintViolation<Issue>> violations = validator.validate(issueWithLongDescription);

        assertThat(violations).isEmpty();
    }

    @Test
    void testOptionalFieldsCanBeNull() {
        Issue issueWithNullOptionalFields = new Issue();
        issueWithNullOptionalFields.setKey("PROJ-1");
        issueWithNullOptionalFields.setTitle("Title");
        issueWithNullOptionalFields.setReporter(reporter);
        issueWithNullOptionalFields.setProject(project);
        // All optional fields are null

        Set<ConstraintViolation<Issue>> violations = validator.validate(issueWithNullOptionalFields);

        assertThat(violations).isEmpty();
    }
}
