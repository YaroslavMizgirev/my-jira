package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class WorkflowStatusTest {

    private WorkflowStatus workflowStatus;
    private Workflow testWorkflow;
    private IssueStatus testIssueStatus;

    private WorkflowStatus ws(Workflow w, IssueStatus s) {
        Long wId = w != null ? w.getId() : null;
        Long sId = s != null ? s.getId() : null;
        return new WorkflowStatus(new WorkflowStatus.WorkflowStatusId(wId, sId), w, s);
    }

    @BeforeEach
    void setUp() {
        testWorkflow = Workflow.builder()
                .id(1L)
                .name("Development Workflow")
                .isDefault(false)
                .build();

        testIssueStatus = IssueStatus.builder()
                .id(2L)
                .name("IN_PROGRESS")
                .build();

        workflowStatus = ws(testWorkflow, testIssueStatus);
    }

    @Test
    @DisplayName("Should create WorkflowStatus with no-args constructor")
    void testNoArgsConstructor() {
        WorkflowStatus emptyWorkflowStatus = new WorkflowStatus();

        assertNotNull(emptyWorkflowStatus);
        assertNull(emptyWorkflowStatus.getWorkflow());
        assertNull(emptyWorkflowStatus.getIssueStatus());
    }

    @Test
    @DisplayName("Should create WorkflowStatus with all-args constructor")
    void testAllArgsConstructor() {
        assertNotNull(workflowStatus);
        assertEquals(testWorkflow, workflowStatus.getWorkflow());
        assertEquals(testIssueStatus, workflowStatus.getIssueStatus());
    }

    @Test
    @DisplayName("Should create WorkflowStatus using builder pattern")
    void testBuilderPattern() {
        WorkflowStatus builtWorkflowStatus = WorkflowStatus.builder()
                .workflow(testWorkflow)
                .issueStatus(testIssueStatus)
                .build();

        assertNotNull(builtWorkflowStatus);
        assertEquals(testWorkflow, builtWorkflowStatus.getWorkflow());
        assertEquals(testIssueStatus, builtWorkflowStatus.getIssueStatus());
    }

    @Test
    @DisplayName("Should set and get workflow")
    void testWorkflowGetterAndSetter() {
        WorkflowStatus status = new WorkflowStatus();
        Workflow newWorkflow = Workflow.builder()
                .id(3L)
                .name("Bug Workflow")
                .isDefault(true)
                .build();

        status.setWorkflow(newWorkflow);

        assertEquals(newWorkflow, status.getWorkflow());
        assertEquals(3L, status.getWorkflow().getId());
        assertEquals("Bug Workflow", status.getWorkflow().getName());
    }

    @Test
    @DisplayName("Should set and get issueStatus")
    void testIssueStatusGetterAndSetter() {
        WorkflowStatus status = new WorkflowStatus();
        IssueStatus newIssueStatus = IssueStatus.builder()
                .id(4L)
                .name("RESOLVED")
                .build();

        status.setIssueStatus(newIssueStatus);

        assertEquals(newIssueStatus, status.getIssueStatus());
        assertEquals(4L, status.getIssueStatus().getId());
        assertEquals("RESOLVED", status.getIssueStatus().getName());
    }

    @Test
    @DisplayName("Should implement equals based on composite key")
    void testEquals() {
        WorkflowStatus sameCompositeKey = ws(testWorkflow, testIssueStatus);

        Workflow differentWorkflow = Workflow.builder().id(5L).name("Different Workflow").build();
        WorkflowStatus differentWorkflowKey = ws(differentWorkflow, testIssueStatus);

        IssueStatus differentStatus = IssueStatus.builder().id(6L).name("CLOSED").build();
        WorkflowStatus differentStatusKey = ws(testWorkflow, differentStatus);

        WorkflowStatus completelyDifferent = ws(differentWorkflow, differentStatus);

        assertEquals(workflowStatus, sameCompositeKey,
                "Objects with same workflow and status should be equal");
        assertNotEquals(workflowStatus, differentWorkflowKey,
                "Objects with different workflows should not be equal");
        assertNotEquals(workflowStatus, differentStatusKey,
                "Objects with different statuses should not be equal");
        assertNotEquals(workflowStatus, completelyDifferent,
                "Completely different objects should not be equal");
        assertNotEquals(workflowStatus, null,
                "Object should not be equal to null");
        assertNotEquals(workflowStatus, new Object(),
                "Object should not be equal to different type");

        // Reflexivity
        assertEquals(workflowStatus, workflowStatus,
                "Object should be equal to itself");

        // Symmetry
        assertEquals(sameCompositeKey, workflowStatus,
                "Equals should be symmetric");
    }

    @Test
    @DisplayName("Should implement hashCode based on composite key")
    void testHashCode() {
        WorkflowStatus sameCompositeKey = ws(testWorkflow, testIssueStatus);

        Workflow differentWorkflow = Workflow.builder().id(5L).name("Different").build();
        WorkflowStatus differentWorkflowKey = ws(differentWorkflow, testIssueStatus);

        assertEquals(workflowStatus.hashCode(), sameCompositeKey.hashCode(),
                "Objects with same composite key should have same hashCode");
        assertNotEquals(workflowStatus.hashCode(), differentWorkflowKey.hashCode(),
                "Objects with different composite keys should have different hashCode");

        int firstHashCode = workflowStatus.hashCode();
        int secondHashCode = workflowStatus.hashCode();
        assertEquals(firstHashCode, secondHashCode,
                "HashCode should be consistent");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        String toString = workflowStatus.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("WorkflowStatus"));
    }

    @Test
    @DisplayName("Should work correctly as JPA entity with composite key")
    void testEntityBehavior() {
        WorkflowStatus status = WorkflowStatus.builder()
                .id(new WorkflowStatus.WorkflowStatusId(testWorkflow.getId(), testIssueStatus.getId()))
                .workflow(testWorkflow)
                .issueStatus(testIssueStatus)
                .build();

        assertNotNull(status);
        assertEquals(testWorkflow, status.getWorkflow());
        assertEquals(testIssueStatus, status.getIssueStatus());
        assertEquals(testWorkflow.getId(), status.getWorkflow().getId());
        assertEquals(testIssueStatus.getId(), status.getIssueStatus().getId());
    }

    @Test
    @DisplayName("Should test composite key class WorkflowStatusId")
    void testCompositeKeyClass() {
        WorkflowStatus.WorkflowStatusId key1 = new WorkflowStatus.WorkflowStatusId(1L, 2L);
        WorkflowStatus.WorkflowStatusId key2 = new WorkflowStatus.WorkflowStatusId(1L, 2L);
        WorkflowStatus.WorkflowStatusId key3 = new WorkflowStatus.WorkflowStatusId(3L, 4L);
        WorkflowStatus.WorkflowStatusId key4 = new WorkflowStatus.WorkflowStatusId(1L, 4L);
        WorkflowStatus.WorkflowStatusId key5 = new WorkflowStatus.WorkflowStatusId(3L, 2L);

        WorkflowStatus.WorkflowStatusId defaultKey = new WorkflowStatus.WorkflowStatusId();
        assertNotNull(defaultKey);
        assertNull(defaultKey.getWorkflowId());
        assertNull(defaultKey.getStatusId());

        WorkflowStatus.WorkflowStatusId allArgsKey = new WorkflowStatus.WorkflowStatusId(5L, 6L);
        assertEquals(5L, allArgsKey.getWorkflowId());
        assertEquals(6L, allArgsKey.getStatusId());

        WorkflowStatus.WorkflowStatusId key = new WorkflowStatus.WorkflowStatusId();
        key.setWorkflowId(7L);
        key.setStatusId(8L);
        assertEquals(7L, key.getWorkflowId());
        assertEquals(8L, key.getStatusId());

        assertEquals(key1, key2, "Keys with same values should be equal");
        assertNotEquals(key1, key3, "Keys with different values should not be equal");
        assertNotEquals(key1, key4, "Keys with different statusId should not be equal");
        assertNotEquals(key1, key5, "Keys with different workflowId should not be equal");
        assertNotEquals(key1, null, "Key should not be equal to null");
        assertNotEquals(key1, new Object(), "Key should not be equal to different type");

        assertEquals(key1.hashCode(), key2.hashCode(),
                "Equal keys should have same hashCode");
        assertNotEquals(key1.hashCode(), key3.hashCode(),
                "Different keys should have different hashCode");

        String keyToString = key1.toString();
        assertNotNull(keyToString);
    }

    @Test
    @DisplayName("Should handle null values in composite key")
    void testNullHandlingInCompositeKey() {
        WorkflowStatus.WorkflowStatusId nullWorkflowId = new WorkflowStatus.WorkflowStatusId(null, 2L);
        WorkflowStatus.WorkflowStatusId nullStatusId = new WorkflowStatus.WorkflowStatusId(1L, null);
        WorkflowStatus.WorkflowStatusId bothNull = new WorkflowStatus.WorkflowStatusId(null, null);
        WorkflowStatus.WorkflowStatusId sameNullWorkflow = new WorkflowStatus.WorkflowStatusId(null, 2L);
        WorkflowStatus.WorkflowStatusId sameNullStatus = new WorkflowStatus.WorkflowStatusId(1L, null);
        WorkflowStatus.WorkflowStatusId sameBothNull = new WorkflowStatus.WorkflowStatusId(null, null);

        assertNotEquals(nullWorkflowId, nullStatusId);
        assertNotEquals(nullWorkflowId, bothNull);
        assertNotEquals(nullStatusId, bothNull);

        // Same null patterns should be equal (Lombok @EqualsAndHashCode behaviour)
        assertEquals(nullWorkflowId, sameNullWorkflow);
        assertEquals(nullStatusId, sameNullStatus);
        assertEquals(bothNull, sameBothNull);
    }

    @Test
    @DisplayName("Should test entity with null components")
    void testEntityWithNullComponents() {
        WorkflowStatus nullWorkflow = new WorkflowStatus(
                new WorkflowStatus.WorkflowStatusId(null, testIssueStatus.getId()), null, testIssueStatus);
        WorkflowStatus nullStatus = new WorkflowStatus(
                new WorkflowStatus.WorkflowStatusId(testWorkflow.getId(), null), testWorkflow, null);
        WorkflowStatus bothNull = new WorkflowStatus(
                new WorkflowStatus.WorkflowStatusId(null, null), null, null);

        assertNotNull(nullWorkflow);
        assertNull(nullWorkflow.getWorkflow());
        assertEquals(testIssueStatus, nullWorkflow.getIssueStatus());

        assertNotNull(nullStatus);
        assertEquals(testWorkflow, nullStatus.getWorkflow());
        assertNull(nullStatus.getIssueStatus());

        assertNotNull(bothNull);
        assertNull(bothNull.getWorkflow());
        assertNull(bothNull.getIssueStatus());
    }

    @Test
    @DisplayName("Should test builder with partial parameters")
    void testPartialBuilder() {
        WorkflowStatus onlyWorkflow = WorkflowStatus.builder()
                .workflow(testWorkflow)
                .build();

        WorkflowStatus onlyStatus = WorkflowStatus.builder()
                .issueStatus(testIssueStatus)
                .build();

        WorkflowStatus empty = WorkflowStatus.builder().build();

        assertNotNull(onlyWorkflow);
        assertEquals(testWorkflow, onlyWorkflow.getWorkflow());
        assertNull(onlyWorkflow.getIssueStatus());

        assertNotNull(onlyStatus);
        assertNull(onlyStatus.getWorkflow());
        assertEquals(testIssueStatus, onlyStatus.getIssueStatus());

        assertNotNull(empty);
        assertNull(empty.getWorkflow());
        assertNull(empty.getIssueStatus());
    }

    @Test
    @DisplayName("Should verify many-to-one relationship constraints")
    void testManyToOneConstraints() {
        WorkflowStatus status = new WorkflowStatus();

        status.setWorkflow(testWorkflow);
        status.setIssueStatus(testIssueStatus);

        assertEquals(testWorkflow, status.getWorkflow());
        assertEquals(testIssueStatus, status.getIssueStatus());
        assertNotNull(status.getWorkflow().getName());
        assertNotNull(status.getIssueStatus().getName());
    }

    @Test
    @DisplayName("Should test different workflow and status combinations")
    void testDifferentCombinations() {
        Workflow workflow1 = Workflow.builder().id(1L).name("Workflow 1").build();
        Workflow workflow2 = Workflow.builder().id(2L).name("Workflow 2").build();

        IssueStatus status1 = IssueStatus.builder().id(1L).name("OPEN").build();
        IssueStatus status2 = IssueStatus.builder().id(2L).name("CLOSED").build();
        IssueStatus status3 = IssueStatus.builder().id(3L).name("IN_REVIEW").build();

        WorkflowStatus combo1 = ws(workflow1, status1);
        WorkflowStatus combo2 = ws(workflow1, status2);
        WorkflowStatus combo3 = ws(workflow2, status1);
        WorkflowStatus combo4 = ws(workflow2, status3);

        assertNotEquals(combo1, combo2);
        assertNotEquals(combo1, combo3);
        assertNotEquals(combo1, combo4);
        assertNotEquals(combo2, combo3);
        assertNotEquals(combo2, combo4);
        assertNotEquals(combo3, combo4);

        assertNotEquals(combo1.hashCode(), combo2.hashCode());
        assertNotEquals(combo1.hashCode(), combo3.hashCode());
        assertNotEquals(combo1.hashCode(), combo4.hashCode());
    }
}
