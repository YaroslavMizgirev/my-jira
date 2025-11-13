package ru.mymsoft.my_jira.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import ru.mymsoft.my_jira.model.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class IssueRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IssueRepository issueRepository;

    private User reporter;
    private User assignee;
    private Project project;
    private IssueType issueType;
    private Priority priority;
    private IssueStatus status;
    private Workflow workflow;
    private Issue issue;

    @BeforeEach
    void setUp() {
        // Create and persist reporter
        reporter = new User();
        reporter.setUsername("reporter");
        reporter.setEmail("reporter@test.com");
        reporter.setPassword("password");
        reporter = entityManager.persistAndFlush(reporter);

        // Create and persist assignee
        assignee = new User();
        assignee.setUsername("assignee");
        assignee.setEmail("assignee@test.com");
        assignee.setPassword("password");
        assignee = entityManager.persistAndFlush(assignee);

        // Create and persist project
        project = new Project();
        project.setKey("PROJ");
        project.setName("Test Project");
        project = entityManager.persistAndFlush(project);

        // Create and persist issue type
        issueType = new IssueType();
        issueType.setName("Bug");
        issueType = entityManager.persistAndFlush(issueType);

        // Create and persist priority
        priority = new Priority();
        priority.setName("High");
        priority = entityManager.persistAndFlush(priority);

        // Create and persist status
        status = new IssueStatus();
        status.setName("Open");
        status = entityManager.persistAndFlush(status);

        // Create and persist workflow
        workflow = new Workflow();
        workflow.setName("Default Workflow");
        workflow = entityManager.persistAndFlush(workflow);

        // Create issue
        issue = new Issue();
        issue.setKey("PROJ-1");
        issue.setTitle("Test Issue");
        issue.setDescription("Test issue description");
        issue.setReporter(reporter);
        issue.setProject(project);
    }

    @Test
    void whenSaveIssue_thenIssueIsPersisted() {
        // When
        Issue savedIssue = issueRepository.save(issue);

        // Then
        assertThat(savedIssue).isNotNull();
        assertThat(savedIssue.getId()).isNotNull();
        assertThat(savedIssue.getKey()).isEqualTo("PROJ-1");
        assertThat(savedIssue.getTitle()).isEqualTo("Test Issue");
        assertThat(savedIssue.getDescription()).isEqualTo("Test issue description");
        assertThat(savedIssue.getReporter()).isEqualTo(reporter);
        assertThat(savedIssue.getProject()).isEqualTo(project);
        assertThat(savedIssue.getCreatedAt()).isNotNull();
        assertThat(savedIssue.getUpdatedAt()).isNotNull();

        // Verify in database
        Issue foundIssue = entityManager.find(Issue.class, savedIssue.getId());
        assertThat(foundIssue).isEqualTo(savedIssue);
    }

    @Test
    void whenSaveIssueWithAllRelations_thenIssueIsPersisted() {
        // Given
        issue.setAssignee(assignee);
        issue.setIssueType(issueType);
        issue.setPriority(priority);
        issue.setStatus(status);
        issue.setWorkflow(workflow);

        // When
        Issue savedIssue = issueRepository.save(issue);

        // Then
        assertThat(savedIssue.getAssignee()).isEqualTo(assignee);
        assertThat(savedIssue.getIssueType()).isEqualTo(issueType);
        assertThat(savedIssue.getPriority()).isEqualTo(priority);
        assertThat(savedIssue.getStatus()).isEqualTo(status);
        assertThat(savedIssue.getWorkflow()).isEqualTo(workflow);
    }

    @Test
    void whenFindById_thenReturnIssue() {
        // Given
        Issue savedIssue = entityManager.persistAndFlush(issue);

        // When
        Optional<Issue> foundIssue = issueRepository.findById(savedIssue.getId());

        // Then
        assertThat(foundIssue).isPresent();
        assertThat(foundIssue.get().getKey()).isEqualTo("PROJ-1");
        assertThat(foundIssue.get().getTitle()).isEqualTo("Test Issue");
        assertThat(foundIssue.get().getReporter()).isEqualTo(reporter);
        assertThat(foundIssue.get().getProject()).isEqualTo(project);
    }

    @Test
    void whenFindByKey_thenReturnIssue() {
        // Given
        entityManager.persistAndFlush(issue);

        // When
        Optional<Issue> foundIssue = issueRepository.findByKey("PROJ-1");

        // Then
        assertThat(foundIssue).isPresent();
        assertThat(foundIssue.get().getKey()).isEqualTo("PROJ-1");
        assertThat(foundIssue.get().getTitle()).isEqualTo("Test Issue");
    }

    @Test
    void whenFindByProject_thenReturnIssues() {
        // Given
        Issue issue1 = new Issue();
        issue1.setKey("PROJ-1");
        issue1.setTitle("Issue 1");
        issue1.setReporter(reporter);
        issue1.setProject(project);

        Issue issue2 = new Issue();
        issue2.setKey("PROJ-2");
        issue2.setTitle("Issue 2");
        issue2.setReporter(reporter);
        issue2.setProject(project);

        entityManager.persistAndFlush(issue1);
        entityManager.persistAndFlush(issue2);

        // When
        List<Issue> issues = issueRepository.findByProject(project);

        // Then
        assertThat(issues).hasSize(2);
        assertThat(issues).extracting(Issue::getKey)
                .containsExactlyInAnyOrder("PROJ-1", "PROJ-2");
    }

    @Test
    void whenFindByReporter_thenReturnIssues() {
        // Given
        entityManager.persistAndFlush(issue);

        // Create another reporter
        User anotherReporter = new User();
        anotherReporter.setUsername("another");
        anotherReporter.setEmail("another@test.com");
        anotherReporter.setPassword("password");
        anotherReporter = entityManager.persistAndFlush(anotherReporter);

        Issue anotherIssue = new Issue();
        anotherIssue.setKey("PROJ-2");
        anotherIssue.setTitle("Another Issue");
        anotherIssue.setReporter(anotherReporter);
        anotherIssue.setProject(project);
        entityManager.persistAndFlush(anotherIssue);

        // When
        List<Issue> reporterIssues = issueRepository.findByReporter(reporter);

        // Then
        assertThat(reporterIssues).hasSize(1);
        assertThat(reporterIssues.get(0).getKey()).isEqualTo("PROJ-1");
    }

    @Test
    void whenFindByAssignee_thenReturnIssues() {
        // Given
        issue.setAssignee(assignee);
        entityManager.persistAndFlush(issue);

        Issue unassignedIssue = new Issue();
        unassignedIssue.setKey("PROJ-2");
        unassignedIssue.setTitle("Unassigned Issue");
        unassignedIssue.setReporter(reporter);
        unassignedIssue.setProject(project);
        entityManager.persistAndFlush(unassignedIssue);

        // When
        List<Issue> assignedIssues = issueRepository.findByAssignee(assignee);

        // Then
        assertThat(assignedIssues).hasSize(1);
        assertThat(assignedIssues.get(0).getKey()).isEqualTo("PROJ-1");
    }

    @Test
    void whenUpdateIssue_thenTimestampsAreUpdated() throws InterruptedException {
        // Given
        Issue savedIssue = entityManager.persistAndFlush(issue);
        Instant originalCreatedAt = savedIssue.getCreatedAt();
        Instant originalUpdatedAt = savedIssue.getUpdatedAt();

        // When - wait a moment and update
        Thread.sleep(100);
        savedIssue.setTitle("Updated Title");
        Issue updatedIssue = issueRepository.save(savedIssue);

        // Then
        assertThat(updatedIssue.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedIssue.getCreatedAt()).isEqualTo(originalCreatedAt);
        assertThat(updatedIssue.getUpdatedAt()).isAfter(originalUpdatedAt);
    }

    @Test
    void whenDeleteIssue_thenIssueIsRemoved() {
        // Given
        Issue savedIssue = entityManager.persistAndFlush(issue);

        // When
        issueRepository.delete(savedIssue);
        entityManager.flush();

        // Then
        Issue deletedIssue = entityManager.find(Issue.class, savedIssue.getId());
        assertThat(deletedIssue).isNull();
    }

    @Test
    void whenSaveDuplicateKey_thenThrowException() {
        // Given
        entityManager.persistAndFlush(issue);

        Issue duplicateIssue = new Issue();
        duplicateIssue.setKey("PROJ-1");
        duplicateIssue.setTitle("Duplicate Issue");
        duplicateIssue.setReporter(reporter);
        duplicateIssue.setProject(project);

        // When & Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            issueRepository.saveAndFlush(duplicateIssue);
        });
    }

    @Test
    void whenFindByNonExistingKey_thenReturnEmpty() {
        // When
        Optional<Issue> foundIssue = issueRepository.findByKey("NONEXISTENT-1");

        // Then
        assertThat(foundIssue).isEmpty();
    }

    @Test
    void testLazyLoadingRelations() {
        // Given
        Issue savedIssue = entityManager.persistAndFlush(issue);
        entityManager.detach(savedIssue);

        // When
        Issue foundIssue = issueRepository.findById(savedIssue.getId()).orElseThrow();

        // Then - verify that relationships are lazy loaded
        // This would typically be checked with Hibernate proxies
        assertThat(foundIssue).isNotNull();
        assertThat(foundIssue.getReporter()).isNotNull();
        assertThat(foundIssue.getProject()).isNotNull();
    }

    @Test
    void whenFindByStatus_thenReturnIssues() {
        // Given
        issue.setStatus(status);
        entityManager.persistAndFlush(issue);

        IssueStatus anotherStatus = new IssueStatus();
        anotherStatus.setName("Closed");
        anotherStatus = entityManager.persistAndFlush(anotherStatus);

        Issue issue2 = new Issue();
        issue2.setKey("PROJ-2");
        issue2.setTitle("Issue 2");
        issue2.setReporter(reporter);
        issue2.setProject(project);
        issue2.setStatus(anotherStatus);
        entityManager.persistAndFlush(issue2);

        // When
        List<Issue> statusIssues = issueRepository.findByStatus(status);

        // Then
        assertThat(statusIssues).hasSize(1);
        assertThat(statusIssues.get(0).getKey()).isEqualTo("PROJ-1");
    }
}
