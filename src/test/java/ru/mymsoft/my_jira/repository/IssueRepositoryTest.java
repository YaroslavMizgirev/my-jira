package ru.mymsoft.my_jira.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.mymsoft.my_jira.model.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional
class IssueRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

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

    private <T> T persistAndFlush(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @BeforeEach
    void setUp() {
        reporter = new User();
        reporter.setUsername("reporter");
        reporter.setEmail("reporter@test.com");
        reporter.setPasswordHash("password");
        reporter = persistAndFlush(reporter);

        assignee = new User();
        assignee.setUsername("assignee");
        assignee.setEmail("assignee@test.com");
        assignee.setPasswordHash("password");
        assignee = persistAndFlush(assignee);

        project = new Project();
        project.setKey("PROJ");
        project.setName("Test Project");
        project = persistAndFlush(project);

        issueType = new IssueType();
        issueType.setName("Bug");
        issueType = persistAndFlush(issueType);

        priority = new Priority();
        priority.setName("High");
        priority = persistAndFlush(priority);

        status = new IssueStatus();
        status.setName("Open");
        status = persistAndFlush(status);

        workflow = new Workflow();
        workflow.setName("Default Workflow");
        workflow = persistAndFlush(workflow);

        issue = new Issue();
        issue.setKey("PROJ-1");
        issue.setTitle("Test Issue");
        issue.setDescription("Test issue description");
        issue.setReporter(reporter);
        issue.setProject(project);
    }

    @Test
    void whenSaveIssue_thenIssueIsPersisted() {
        Issue savedIssue = issueRepository.save(issue);

        assertThat(savedIssue).isNotNull();
        assertThat(savedIssue.getId()).isNotNull();
        assertThat(savedIssue.getKey()).isEqualTo("PROJ-1");
        assertThat(savedIssue.getTitle()).isEqualTo("Test Issue");
        assertThat(savedIssue.getDescription()).isEqualTo("Test issue description");
        assertThat(savedIssue.getReporter()).isEqualTo(reporter);
        assertThat(savedIssue.getProject()).isEqualTo(project);
        assertThat(savedIssue.getCreatedAt()).isNotNull();
        assertThat(savedIssue.getUpdatedAt()).isNotNull();

        Issue foundIssue = entityManager.find(Issue.class, savedIssue.getId());
        assertThat(foundIssue).isEqualTo(savedIssue);
    }

    @Test
    void whenSaveIssueWithAllRelations_thenIssueIsPersisted() {
        issue.setAssignee(assignee);
        issue.setIssueType(issueType);
        issue.setPriority(priority);
        issue.setStatus(status);
        issue.setWorkflow(workflow);

        Issue savedIssue = issueRepository.save(issue);

        assertThat(savedIssue.getAssignee()).isEqualTo(assignee);
        assertThat(savedIssue.getIssueType()).isEqualTo(issueType);
        assertThat(savedIssue.getPriority()).isEqualTo(priority);
        assertThat(savedIssue.getStatus()).isEqualTo(status);
        assertThat(savedIssue.getWorkflow()).isEqualTo(workflow);
    }

    @Test
    void whenFindById_thenReturnIssue() {
        Issue savedIssue = persistAndFlush(issue);

        Optional<Issue> foundIssue = issueRepository.findById(savedIssue.getId());

        assertThat(foundIssue).isPresent();
        assertThat(foundIssue.get().getKey()).isEqualTo("PROJ-1");
        assertThat(foundIssue.get().getTitle()).isEqualTo("Test Issue");
        assertThat(foundIssue.get().getReporter()).isEqualTo(reporter);
        assertThat(foundIssue.get().getProject()).isEqualTo(project);
    }

    @Test
    void whenFindByKey_thenReturnIssue() {
        persistAndFlush(issue);

        Optional<Issue> foundIssue = issueRepository.findByKey("PROJ-1");

        assertThat(foundIssue).isPresent();
        assertThat(foundIssue.get().getKey()).isEqualTo("PROJ-1");
        assertThat(foundIssue.get().getTitle()).isEqualTo("Test Issue");
    }

    @Test
    void whenFindByProject_thenReturnIssues() {
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

        persistAndFlush(issue1);
        persistAndFlush(issue2);

        List<Issue> issues = issueRepository.findByProject(project);

        assertThat(issues).hasSize(2);
        assertThat(issues).extracting(Issue::getKey)
                .containsExactlyInAnyOrder("PROJ-1", "PROJ-2");
    }

    @Test
    void whenFindByReporter_thenReturnIssues() {
        persistAndFlush(issue);

        User anotherReporter = new User();
        anotherReporter.setUsername("another");
        anotherReporter.setEmail("another@test.com");
        anotherReporter.setPasswordHash("password");
        anotherReporter = persistAndFlush(anotherReporter);

        Issue anotherIssue = new Issue();
        anotherIssue.setKey("PROJ-2");
        anotherIssue.setTitle("Another Issue");
        anotherIssue.setReporter(anotherReporter);
        anotherIssue.setProject(project);
        persistAndFlush(anotherIssue);

        List<Issue> reporterIssues = issueRepository.findByReporter(reporter);

        assertThat(reporterIssues).hasSize(1);
        assertThat(reporterIssues.get(0).getKey()).isEqualTo("PROJ-1");
    }

    @Test
    void whenFindByAssignee_thenReturnIssues() {
        issue.setAssignee(assignee);
        persistAndFlush(issue);

        Issue unassignedIssue = new Issue();
        unassignedIssue.setKey("PROJ-2");
        unassignedIssue.setTitle("Unassigned Issue");
        unassignedIssue.setReporter(reporter);
        unassignedIssue.setProject(project);
        persistAndFlush(unassignedIssue);

        List<Issue> assignedIssues = issueRepository.findByAssignee(assignee);

        assertThat(assignedIssues).hasSize(1);
        assertThat(assignedIssues.get(0).getKey()).isEqualTo("PROJ-1");
    }

    @Test
    void whenUpdateIssue_thenTimestampsAreUpdated() throws InterruptedException {
        Issue savedIssue = persistAndFlush(issue);
        Instant originalCreatedAt = savedIssue.getCreatedAt();
        Instant originalUpdatedAt = savedIssue.getUpdatedAt();

        Thread.sleep(100);
        savedIssue.setTitle("Updated Title");
        Issue updatedIssue = issueRepository.saveAndFlush(savedIssue);

        assertThat(updatedIssue.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedIssue.getCreatedAt()).isEqualTo(originalCreatedAt);
        assertThat(updatedIssue.getUpdatedAt()).isAfterOrEqualTo(originalUpdatedAt);
    }

    @Test
    void whenDeleteIssue_thenIssueIsRemoved() {
        Issue savedIssue = persistAndFlush(issue);

        issueRepository.delete(savedIssue);
        entityManager.flush();

        Issue deletedIssue = entityManager.find(Issue.class, savedIssue.getId());
        assertThat(deletedIssue).isNull();
    }

    @Test
    void whenSaveDuplicateKey_thenThrowException() {
        persistAndFlush(issue);

        Issue duplicateIssue = new Issue();
        duplicateIssue.setKey("PROJ-1");
        duplicateIssue.setTitle("Duplicate Issue");
        duplicateIssue.setReporter(reporter);
        duplicateIssue.setProject(project);

        assertThrows(DataIntegrityViolationException.class, () -> {
            issueRepository.saveAndFlush(duplicateIssue);
        });
    }

    @Test
    void whenFindByNonExistingKey_thenReturnEmpty() {
        Optional<Issue> foundIssue = issueRepository.findByKey("NONEXISTENT-1");

        assertThat(foundIssue).isEmpty();
    }

    @Test
    void testLazyLoadingRelations() {
        Issue savedIssue = persistAndFlush(issue);
        entityManager.detach(savedIssue);

        Issue foundIssue = issueRepository.findById(savedIssue.getId()).orElseThrow();

        assertThat(foundIssue).isNotNull();
        assertThat(foundIssue.getReporter()).isNotNull();
        assertThat(foundIssue.getProject()).isNotNull();
    }

    @Test
    void whenFindByStatus_thenReturnIssues() {
        issue.setStatus(status);
        persistAndFlush(issue);

        IssueStatus anotherStatus = new IssueStatus();
        anotherStatus.setName("Closed");
        anotherStatus = persistAndFlush(anotherStatus);

        Issue issue2 = new Issue();
        issue2.setKey("PROJ-2");
        issue2.setTitle("Issue 2");
        issue2.setReporter(reporter);
        issue2.setProject(project);
        issue2.setStatus(anotherStatus);
        persistAndFlush(issue2);

        List<Issue> statusIssues = issueRepository.findByStatus(status);

        assertThat(statusIssues).hasSize(1);
        assertThat(statusIssues.get(0).getKey()).isEqualTo("PROJ-1");
    }
}
