package ru.mymsoft.my_jira.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class WorkflowStatusTest {

    @Test
    @DisplayName("Should create WorkflowStatus with no-args constructor")
    void testNoArgsConstructor() {
        // When
        WorkflowStatus workflowStatus = new WorkflowStatus();

        // Then
        assertNotNull(workflowStatus);
        assertNull(workflowStatus.getWorkflowId());
        assertNull(workflowStatus.getStatusId());
    }

    @Test
    @DisplayName("Should create WorkflowStatus with all-args constructor")
    void testAllArgsConstructor() {
        // Given
        Long workflowId = 1L;
        Long statusId = 2L;

        // When
        WorkflowStatus workflowStatus = new WorkflowStatus(workflowId, statusId);

        // Then
        assertNotNull(workflowStatus);
        assertEquals(workflowId, workflowStatus.getWorkflowId());
        assertEquals(statusId, workflowStatus.getStatusId());
    }

    @Test
    @DisplayName("Should create WorkflowStatus using builder pattern")
    void testBuilderPattern() {
        // Given
        Long workflowId = 1L;
        Long statusId = 2L;

        // When
        WorkflowStatus workflowStatus = WorkflowStatus.builder()
                .workflowId(workflowId)
                .statusId(statusId)
                .build();

        // Then
        assertNotNull(workflowStatus);
        assertEquals(workflowId, workflowStatus.getWorkflowId());
        assertEquals(statusId, workflowStatus.getStatusId());
    }

    @Test
    @DisplayName("Should set and get workflowId")
    void testWorkflowIdGetterAndSetter() {
        // Given
        WorkflowStatus workflowStatus = new WorkflowStatus();
        Long workflowId = 5L;

        // When
        workflowStatus.setWorkflowId(workflowId);

        // Then
        assertEquals(workflowId, workflowStatus.getWorkflowId());
    }

    @Test
    @DisplayName("Should set and get statusId")
    void testStatusIdGetterAndSetter() {
        // Given
        WorkflowStatus workflowStatus = new WorkflowStatus();
        Long statusId = 10L;

        // When
        workflowStatus.setStatusId(statusId);

        // Then
        assertEquals(statusId, workflowStatus.getStatusId());
    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void testEqualsAndHashCode() {
        // Given
        WorkflowStatus workflowStatus1 = new WorkflowStatus(1L, 2L);
        WorkflowStatus workflowStatus2 = new WorkflowStatus(1L, 2L);
        WorkflowStatus workflowStatus3 = new WorkflowStatus(3L, 4L);

        // Then - equals
        assertEquals(workflowStatus1, workflowStatus2, "Objects with same values should be equal");
        assertNotEquals(workflowStatus1, workflowStatus3, "Objects with different values should not be equal");
        assertNotEquals(workflowStatus1, null, "Object should not be equal to null");
        assertNotEquals(workflowStatus1, new Object(), "Object should not be equal to different type");

        // Then - hashCode
        assertEquals(workflowStatus1.hashCode(), workflowStatus2.hashCode(),
                "Equal objects should have same hashCode");
        assertNotEquals(workflowStatus1.hashCode(), workflowStatus3.hashCode(),
                "Different objects should have different hashCode");
    }

    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        // Given
        WorkflowStatus workflowStatus = new WorkflowStatus(1L, 2L);

        // When
        String toString = workflowStatus.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("workflowId=1"));
        assertTrue(toString.contains("statusId=2"));
    }

    @Test
    @DisplayName("Should create WorkflowStatusId with no-args constructor")
    void testWorkflowStatusIdNoArgsConstructor() {
        // When
        WorkflowStatus.WorkflowStatusId id = new WorkflowStatus.WorkflowStatusId();

        // Then
        assertNotNull(id);
        assertNull(id.getWorkflowId());
        assertNull(id.getStatusId());
    }

    @Test
    @DisplayName("Should create WorkflowStatusId with all-args constructor")
    void testWorkflowStatusIdAllArgsConstructor() {
        // Given
        Long workflowId = 1L;
        Long statusId = 2L;

        // When
        WorkflowStatus.WorkflowStatusId id = new WorkflowStatus.WorkflowStatusId(workflowId, statusId);

        // Then
        assertNotNull(id);
        assertEquals(workflowId, id.getWorkflowId());
        assertEquals(statusId, id.getStatusId());
    }

    @Test
    @DisplayName("Should set and get WorkflowStatusId fields")
    void testWorkflowStatusIdGettersAndSetters() {
        // Given
        WorkflowStatus.WorkflowStatusId id = new WorkflowStatus.WorkflowStatusId();
        Long workflowId = 5L;
        Long statusId = 10L;

        // When
        id.setWorkflowId(workflowId);
        id.setStatusId(statusId);

        // Then
        assertEquals(workflowId, id.getWorkflowId());
        assertEquals(statusId, id.getStatusId());
    }

    @Test
    @DisplayName("WorkflowStatusId should implement equals and hashCode correctly")
    void testWorkflowStatusIdEqualsAndHashCode() {
        // Given
        WorkflowStatus.WorkflowStatusId id1 = new WorkflowStatus.WorkflowStatusId(1L, 2L);
        WorkflowStatus.WorkflowStatusId id2 = new WorkflowStatus.WorkflowStatusId(1L, 2L);
        WorkflowStatus.WorkflowStatusId id3 = new WorkflowStatus.WorkflowStatusId(3L, 4L);

        // Then - equals
        assertEquals(id1, id2, "Ids with same values should be equal");
        assertNotEquals(id1, id3, "Ids with different values should not be equal");
        assertNotEquals(id1, null, "Id should not be equal to null");
        assertNotEquals(id1, new Object(), "Id should not be equal to different type");

        // Then - hashCode
        assertEquals(id1.hashCode(), id2.hashCode(), "Equal ids should have same hashCode");
        assertNotEquals(id1.hashCode(), id3.hashCode(), "Different ids should have different hashCode");
    }

    @Test
    @DisplayName("WorkflowStatusId should return correct string representation")
    void testWorkflowStatusIdToString() {
        // Given
        WorkflowStatus.WorkflowStatusId id = new WorkflowStatus.WorkflowStatusId(1L, 2L);

        // When
        String toString = id.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("workflowId=1"));
        assertTrue(toString.contains("statusId=2"));
    }

    @Test
    @DisplayName("Should work correctly as JPA entity with composite key")
    void testEntityBehavior() {
        // Given
        WorkflowStatus.WorkflowStatusId compositeKey = new WorkflowStatus.WorkflowStatusId(1L, 2L);
        WorkflowStatus entity = new WorkflowStatus();
        entity.setWorkflowId(compositeKey.getWorkflowId());
        entity.setStatusId(compositeKey.getStatusId());

        // When & Then
        assertEquals(compositeKey.getWorkflowId(), entity.getWorkflowId());
        assertEquals(compositeKey.getStatusId(), entity.getStatusId());

        // Verify the composite key can be used for entity identification
        WorkflowStatus entity2 = new WorkflowStatus(compositeKey.getWorkflowId(), compositeKey.getStatusId());
        assertEquals(entity.getWorkflowId(), entity2.getWorkflowId());
        assertEquals(entity.getStatusId(), entity2.getStatusId());
    }

    @Test
    @DisplayName("Should handle null values in equals and hashCode")
    void testNullHandling() {
        // Given
        WorkflowStatus workflowStatus = new WorkflowStatus(null, null);
        WorkflowStatus workflowStatus2 = new WorkflowStatus(null, null);

        // Then
        assertEquals(workflowStatus, workflowStatus2);
        assertEquals(workflowStatus.hashCode(), workflowStatus2.hashCode());

        // Mixed null and non-null
        WorkflowStatus workflowStatus3 = new WorkflowStatus(1L, null);
        WorkflowStatus workflowStatus4 = new WorkflowStatus(1L, null);
        assertEquals(workflowStatus3, workflowStatus4);

        WorkflowStatus workflowStatus5 = new WorkflowStatus(null, 2L);
        WorkflowStatus workflowStatus6 = new WorkflowStatus(null, 2L);
        assertEquals(workflowStatus5, workflowStatus6);
    }
}
