package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class WorkflowStatusTest {

    private WorkflowStatus workflowStatus;
    private Workflow testWorkflow;
    private IssueStatus testIssueStatus;

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

        workflowStatus = new WorkflowStatus(testWorkflow, testIssueStatus);
    }

    @Test
    @DisplayName("Should create WorkflowStatus with no-args constructor")
    void testNoArgsConstructor() {
        // When
        WorkflowStatus emptyWorkflowStatus = new WorkflowStatus();

        // Then
        assertNotNull(emptyWorkflowStatus);
        assertNull(emptyWorkflowStatus.getWorkflow());
        assertNull(emptyWorkflowStatus.getIssueStatus());
    }

    @Test
    @DisplayName("Should create WorkflowStatus with all-args constructor")
    void testAllArgsConstructor() {
        // Then
        assertNotNull(workflowStatus);
        assertEquals(testWorkflow, workflowStatus.getWorkflow());
        assertEquals(testIssueStatus, workflowStatus.getIssueStatus());
    }

    @Test
    @DisplayName("Should create WorkflowStatus using builder pattern")
    void testBuilderPattern() {
        // When
        WorkflowStatus builtWorkflowStatus = WorkflowStatus.builder()
                .workflow(testWorkflow)
                .issueStatus(testIssueStatus)
                .build();

        // Then
        assertNotNull(builtWorkflowStatus);
        assertEquals(testWorkflow, builtWorkflowStatus.getWorkflow());
        assertEquals(testIssueStatus, builtWorkflowStatus.getIssueStatus());
    }

    @Test
    @DisplayName("Should set and get workflow")
    void testWorkflowGetterAndSetter() {
        // Given
        WorkflowStatus status = new WorkflowStatus();
        Workflow newWorkflow = Workflow.builder()
                .id(3L)
                .name("Bug Workflow")
                .isDefault(true)
                .build();

        // When
        status.setWorkflow(newWorkflow);

        // Then
        assertEquals(newWorkflow, status.getWorkflow());
        assertEquals(3L, status.getWorkflow().getId());
        assertEquals("Bug Workflow", status.getWorkflow().getName());
    }

    @Test
    @DisplayName("Should set and get issueStatus")
    void testIssueStatusGetterAndSetter() {
        // Given
        WorkflowStatus status = new WorkflowStatus();
        IssueStatus newIssueStatus = IssueStatus.builder()
                .id(4L)
                .name("RESOLVED")
                .build();

        // When
        status.setIssueStatus(newIssueStatus);

        // Then
        assertEquals(newIssueStatus, status.getIssueStatus());
        assertEquals(4L, status.getIssueStatus().getId());
        assertEquals("RESOLVED", status.getIssueStatus().getName());
    }

    @Test
    @DisplayName("Should implement equals based on composite key")
    void testEquals() {
        // Given
        WorkflowStatus sameCompositeKey = new WorkflowStatus(testWorkflow, testIssueStatus);

        Workflow differentWorkflow = Workflow.builder().id(5L).name("Different Workflow").build();
        WorkflowStatus differentWorkflowKey = new WorkflowStatus(differentWorkflow, testIssueStatus);

        IssueStatus differentStatus = IssueStatus.builder().id(6L).name("CLOSED").build();
        WorkflowStatus differentStatusKey = new WorkflowStatus(testWorkflow, differentStatus);

        WorkflowStatus completelyDifferent = new WorkflowStatus(differentWorkflow, differentStatus);

        WorkflowStatus nullWorkflow = new WorkflowStatus(null, testIssueStatus);
        WorkflowStatus nullStatus = new WorkflowStatus(testWorkflow, null);
        WorkflowStatus bothNull = new WorkflowStatus(null, null);

        // Then
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

        // Null handling
        assertNotEquals(workflowStatus, nullWorkflow);
        assertNotEquals(workflowStatus, nullStatus);
        assertNotEquals(workflowStatus, bothNull);
        assertNotEquals(nullWorkflow, nullStatus);
    }

    @Test
    @DisplayName("Should implement hashCode based on composite key")
    void testHashCode() {
        // Given
        WorkflowStatus sameCompositeKey = new WorkflowStatus(testWorkflow, testIssueStatus);

        Workflow differentWorkflow = Workflow.builder().id(5L).name("Different").build();
        WorkflowStatus differentWorkflowKey = new WorkflowStatus(differentWorkflow, testIssueStatus);

        // Then
        assertEquals(workflowStatus.hashCode(), sameCompositeKey.hashCode(),
                "Objects with same composite key should have same hashCode");
        assertNotEquals(workflowStatus.hashCode(), differentWorkflowKey.hashCode(),
                "Objects with different composite keys should have different hashCode");

        // Consistency
        int firstHashCode = workflowStatus.hashCode();
        int secondHashCode = workflowStatus.hashCode();
        assertEquals(firstHashCode, secondHashCode,
                "HashCode should be consistent");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        // When
        String toString = workflowStatus.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("WorkflowStatus"));
        assertTrue(toString.contains("workflow=" + testWorkflow));
        assertTrue(toString.contains("issueStatus=" + testIssueStatus));
    }

    @Test
    @DisplayName("Should work correctly as JPA entity with composite key")
    void testEntityBehavior() {
        // Given
        WorkflowStatus status = WorkflowStatus.builder()
                .workflow(testWorkflow)
                .issueStatus(testIssueStatus)
                .build();

        // When & Then
        assertNotNull(status);
        assertEquals(testWorkflow, status.getWorkflow());
        assertEquals(testIssueStatus, status.getIssueStatus());

        // Проверяем составной ключ
        assertEquals(testWorkflow.getId(), status.getWorkflow().getId());
        assertEquals(testIssueStatus.getId(), status.getIssueStatus().getId());
    }

    @Test
    @DisplayName("Should test composite key class WorkflowStatusId")
    void testCompositeKeyClass() {
        // Given
        WorkflowStatusId key1 = new WorkflowStatusId(1L, 2L);
        WorkflowStatusId key2 = new WorkflowStatusId(1L, 2L);
        WorkflowStatusId key3 = new WorkflowStatusId(3L, 4L);
        WorkflowStatusId key4 = new WorkflowStatusId(1L, 4L);
        WorkflowStatusId key5 = new WorkflowStatusId(3L, 2L);

        // Test constructors
        WorkflowStatusId defaultKey = new WorkflowStatusId();
        assertNotNull(defaultKey);
        assertNull(defaultKey.getWorkflowId());
        assertNull(defaultKey.getStatusId());

        WorkflowStatusId allArgsKey = new WorkflowStatusId(5L, 6L);
        assertEquals(5L, allArgsKey.getWorkflowId());
        assertEquals(6L, allArgsKey.getStatusId());

        // Test getters and setters
        WorkflowStatusId key = new WorkflowStatusId();
        key.setWorkflowId(7L);
        key.setStatusId(8L);
        assertEquals(7L, key.getWorkflowId());
        assertEquals(8L, key.getStatusId());

        // Test equals
        assertEquals(key1, key2, "Keys with same values should be equal");
        assertNotEquals(key1, key3, "Keys with different values should not be equal");
        assertNotEquals(key1, key4, "Keys with different statusId should not be equal");
        assertNotEquals(key1, key5, "Keys with different workflowId should not be equal");
        assertNotEquals(key1, null, "Key should not be equal to null");
        assertNotEquals(key1, new Object(), "Key should not be equal to different type");

        // Test hashCode
        assertEquals(key1.hashCode(), key2.hashCode(),
                "Equal keys should have same hashCode");
        assertNotEquals(key1.hashCode(), key3.hashCode(),
                "Different keys should have different hashCode");

        // Test toString
        String keyToString = key1.toString();
        assertNotNull(keyToString);
        assertTrue(keyToString.contains("workflowId=1"));
        assertTrue(keyToString.contains("statusId=2"));
    }

    @Test
    @DisplayName("Should handle null values in composite key")
    void testNullHandlingInCompositeKey() {
        // Given
        WorkflowStatusId nullWorkflowId = new WorkflowStatusId(null, 2L);
        WorkflowStatusId nullStatusId = new WorkflowStatusId(1L, null);
        WorkflowStatusId bothNull = new WorkflowStatusId(null, null);
        WorkflowStatusId sameNullWorkflow = new WorkflowStatusId(null, 2L);
        WorkflowStatusId sameNullStatus = new WorkflowStatusId(1L, null);
        WorkflowStatusId sameBothNull = new WorkflowStatusId(null, null);

        // Then
        assertNotEquals(nullWorkflowId, nullStatusId);
        assertNotEquals(nullWorkflowId, bothNull);
        assertNotEquals(nullStatusId, bothNull);

        // Objects with same null patterns should not be equal (Lombok behavior)
        assertNotEquals(nullWorkflowId, sameNullWorkflow);
        assertNotEquals(nullStatusId, sameNullStatus);
        assertNotEquals(bothNull, sameBothNull);

        // HashCodes should be different
        assertNotEquals(nullWorkflowId.hashCode(), nullStatusId.hashCode());
        assertNotEquals(nullWorkflowId.hashCode(), bothNull.hashCode());
    }

    @Test
    @DisplayName("Should test entity with null components")
    void testEntityWithNullComponents() {
        // Given
        WorkflowStatus nullWorkflow = new WorkflowStatus(null, testIssueStatus);
        WorkflowStatus nullStatus = new WorkflowStatus(testWorkflow, null);
        WorkflowStatus bothNull = new WorkflowStatus(null, null);

        // Then
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
        // When - builder только с workflow
        WorkflowStatus onlyWorkflow = WorkflowStatus.builder()
                .workflow(testWorkflow)
                .build();

        // When - builder только со status
        WorkflowStatus onlyStatus = WorkflowStatus.builder()
                .issueStatus(testIssueStatus)
                .build();

        // When - пустой builder
        WorkflowStatus empty = WorkflowStatus.builder().build();

        // Then
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
        // Given
        WorkflowStatus status = new WorkflowStatus();

        // When - устанавливаем обязательные связи
        status.setWorkflow(testWorkflow);
        status.setIssueStatus(testIssueStatus);

        // Then - проверяем что связи установлены
        assertEquals(testWorkflow, status.getWorkflow());
        assertEquals(testIssueStatus, status.getIssueStatus());

        // Проверяем LAZY загрузку (опционально, зависит от реализации)
        assertNotNull(status.getWorkflow().getName());
        assertNotNull(status.getIssueStatus().getName());
    }

    @Test
    @DisplayName("Should test different workflow and status combinations")
    void testDifferentCombinations() {
        // Given разные комбинации workflow и status
        Workflow workflow1 = Workflow.builder().id(1L).name("Workflow 1").build();
        Workflow workflow2 = Workflow.builder().id(2L).name("Workflow 2").build();

        IssueStatus status1 = IssueStatus.builder().id(1L).name("OPEN").build();
        IssueStatus status2 = IssueStatus.builder().id(2L).name("CLOSED").build();
        IssueStatus status3 = IssueStatus.builder().id(3L).name("IN_REVIEW").build();

        // When создаем разные комбинации
        WorkflowStatus combo1 = new WorkflowStatus(workflow1, status1);
        WorkflowStatus combo2 = new WorkflowStatus(workflow1, status2);
        WorkflowStatus combo3 = new WorkflowStatus(workflow2, status1);
        WorkflowStatus combo4 = new WorkflowStatus(workflow2, status3);

        // Then все комбинации уникальны
        assertNotEquals(combo1, combo2);
        assertNotEquals(combo1, combo3);
        assertNotEquals(combo1, combo4);
        assertNotEquals(combo2, combo3);
        assertNotEquals(combo2, combo4);
        assertNotEquals(combo3, combo4);

        // Проверяем hashCode также различается
        assertNotEquals(combo1.hashCode(), combo2.hashCode());
        assertNotEquals(combo1.hashCode(), combo3.hashCode());
        assertNotEquals(combo1.hashCode(), combo4.hashCode());
    }
}
